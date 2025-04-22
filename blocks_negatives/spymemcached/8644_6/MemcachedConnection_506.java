public final class MemcachedConnection extends SpyThread implements Reconfigurable {

	private static final int DOUBLE_CHECK_EMPTY = 256;
	private static final int EXCESSIVE_EMPTY = 0x1000000;

	private volatile boolean shutDown=false;
	private final boolean shouldOptimize;
	private Selector selector=null;
	private final NodeLocator locator;
	private final FailureMode failureMode;
	private final long maxDelay;
	private int emptySelects=0;
	private final int bufSize;
	private final ConnectionFactory connectionFactory;
	private final ConcurrentLinkedQueue<MemcachedNode> addedQueue;
	private final SortedMap<Long, MemcachedNode> reconnectQueue;

	protected volatile boolean reconfiguring = false;
	protected volatile boolean running=true;

	private final Collection<ConnectionObserver> connObservers =
		new ConcurrentLinkedQueue<ConnectionObserver>();
	private final OperationFactory opFact;
	private final int timeoutExceptionThreshold;
        private final Collection<Operation> retryOps;
	private final ConcurrentLinkedQueue<MemcachedNode> nodesToShutdown;

	/**
	 * Construct a memcached connection.
	 *
	 * @param bufSize the size of the buffer used for reading from the server
	 * @param f the factory that will provide an operation queue
	 * @param a the addresses of the servers to connect to
	 *
	 * @throws IOException if a connection attempt fails early
	 */
	public MemcachedConnection(int bufSize, ConnectionFactory f,
			List<InetSocketAddress> a, Collection<ConnectionObserver> obs,
			FailureMode fm, OperationFactory opfactory)
		throws IOException {
		connObservers.addAll(obs);
		reconnectQueue=new TreeMap<Long, MemcachedNode>();
		addedQueue=new ConcurrentLinkedQueue<MemcachedNode>();
		failureMode = fm;
		shouldOptimize = f.shouldOptimize();
		maxDelay = f.getMaxReconnectDelay();
		opFact = opfactory;
		timeoutExceptionThreshold = f.getTimeoutExceptionThreshold();
		selector=Selector.open();
		retryOps = new ArrayList<Operation>();
		nodesToShutdown = new ConcurrentLinkedQueue<MemcachedNode>();
		this.bufSize = bufSize;
		this.connectionFactory = f;
		List<MemcachedNode> connections = createConnections(a);
		locator=f.createLocator(connections);
		setName("Memcached IO over " + this);
		setDaemon(f.isDaemon());
		start();
		}

	private List<MemcachedNode> createConnections(final Collection<InetSocketAddress> a)
		throws IOException {
		List<MemcachedNode> connections=new ArrayList<MemcachedNode>(a.size());
		for(SocketAddress sa : a) {
			SocketChannel ch=SocketChannel.open();
			ch.configureBlocking(false);
			MemcachedNode qa=this.connectionFactory.createMemcachedNode(sa, ch, bufSize);
			int ops=0;
			ch.socket().setTcpNoDelay(!this.connectionFactory.useNagleAlgorithm());
			try {
				if(ch.connect(sa)) {
					getLogger().info("Connected to %s immediately", qa);
					connected(qa);
				} else {
					getLogger().info("Added %s to connect queue", qa);
					ops=SelectionKey.OP_CONNECT;
				}
				qa.setSk(ch.register(selector, ops, qa));
				assert ch.isConnected()
					|| qa.getSk().interestOps() == SelectionKey.OP_CONNECT
					: "Not connected, and not wanting to connect";
			} catch(SocketException e) {
				getLogger().warn("Socket error on initial connect", e);
				queueReconnect(qa);
			}
			connections.add(qa);
		}
		return connections;
	}

	public void reconfigure(Bucket bucket) {
		reconfiguring = true;
		try {
			List<String> servers = bucket.getConfig().getServers();
			HashSet<SocketAddress> newServerAddresses = new HashSet<SocketAddress>();
			ArrayList<InetSocketAddress> newServers = new ArrayList<InetSocketAddress>();
			for (String server : servers) {
				int finalColon = server.lastIndexOf(':');
				if (finalColon < 1) {
					throw new IllegalArgumentException("Invalid server ``"
					+ server + "'' in vbucket's server list");
				}
				String hostPart = server.substring(0, finalColon);
				String portNum = server.substring(finalColon + 1);

				InetSocketAddress address = new InetSocketAddress(hostPart,
				Integer.parseInt(portNum));
				newServerAddresses.add(address);
				newServers.add(address);

			}

			ArrayList<MemcachedNode> oddNodes = new ArrayList<MemcachedNode>();
			ArrayList<MemcachedNode> stayNodes = new ArrayList<MemcachedNode>();
			ArrayList<InetSocketAddress> stayServers = new ArrayList<InetSocketAddress>();
			for (MemcachedNode current : locator.getAll()) {
				if (newServerAddresses.contains(current.getSocketAddress())) {
					stayNodes.add(current);
					stayServers.add((InetSocketAddress) current.getSocketAddress());
				} else {
					oddNodes.add(current);
				}
			}

			newServers.removeAll(stayServers);

			List<MemcachedNode> newNodes = createConnections(newServers);

			List<MemcachedNode> mergedNodes = new ArrayList<MemcachedNode>();
			mergedNodes.addAll(stayNodes);
			mergedNodes.addAll(newNodes);

			locator.updateLocator(mergedNodes, bucket.getConfig());

			nodesToShutdown.addAll(oddNodes);
		} catch (IOException e) {
		    getLogger().error("Connection reconfiguration failed", e);
		} finally {
			reconfiguring = false;
		}
	}

	private boolean selectorsMakeSense() {
		for(MemcachedNode qa : locator.getAll()) {
			if(qa.getSk() != null && qa.getSk().isValid()) {
				if(qa.getChannel().isConnected()) {
					int sops=qa.getSk().interestOps();
					int expected=0;
					if(qa.hasReadOp()) {
						expected |= SelectionKey.OP_READ;
					}
					if(qa.hasWriteOp()) {
						expected |= SelectionKey.OP_WRITE;
					}
					if(qa.getBytesRemainingToWrite() > 0) {
						expected |= SelectionKey.OP_WRITE;
					}
					assert sops == expected : "Invalid ops:  "
						+ qa + ", expected " + expected + ", got " + sops;
				} else {
					int sops=qa.getSk().interestOps();
					assert sops == SelectionKey.OP_CONNECT
					: "Not connected, and not watching for connect: "
						+ sops;
				}
			}
		}
		getLogger().debug("Checked the selectors.");
		return true;
	}

	/**
	 * MemcachedClient calls this method to handle IO over the connections.
	 */
	public void handleIO() throws IOException {
		if(shutDown) {
			throw new IOException("No IO while shut down");
		}

		handleInputQueue();
		getLogger().debug("Done dealing with queue.");

		long delay=0;
		if(!reconnectQueue.isEmpty()) {
			long now=System.currentTimeMillis();
			long then=reconnectQueue.firstKey();
			delay=Math.max(then-now, 1);
		}
		getLogger().debug("Selecting with delay of %sms", delay);
		assert selectorsMakeSense() : "Selectors don't make sense.";
		int selected=selector.select(delay);
		Set<SelectionKey> selectedKeys=selector.selectedKeys();

		if(selectedKeys.isEmpty() && !shutDown) {
			getLogger().debug("No selectors ready, interrupted: "
					+ Thread.interrupted());
			if(++emptySelects > DOUBLE_CHECK_EMPTY) {
				for(SelectionKey sk : selector.keys()) {
					getLogger().info("%s has %s, interested in %s",
							sk, sk.readyOps(), sk.interestOps());
					if(sk.readyOps() != 0) {
						getLogger().info("%s has a ready op, handling IO", sk);
						handleIO(sk);
					} else {
						lostConnection((MemcachedNode)sk.attachment());
					}
				}
				assert emptySelects < EXCESSIVE_EMPTY
					: "Too many empty selects";
			}
		} else {
			getLogger().debug("Selected %d, selected %d keys",
					selected, selectedKeys.size());
			emptySelects=0;

			for(SelectionKey sk : selectedKeys) {
				handleIO(sk);
			}

			selectedKeys.clear();
		}


		for(SelectionKey sk : selector.keys()) {
			MemcachedNode mn = (MemcachedNode)sk.attachment();
			if (mn.getContinuousTimeout() > timeoutExceptionThreshold)
			{
				getLogger().warn("%s exceeded continuous timeout threshold", sk);
				lostConnection(mn);
			}
		}

		if(!shutDown && !reconnectQueue.isEmpty()) {
			attemptReconnects();
		}
        redistributeOperations(retryOps);
        retryOps.clear();

        for (MemcachedNode qa : nodesToShutdown) {
            if (!addedQueue.contains(qa)) {
                nodesToShutdown.remove(qa);
                Collection<Operation> notCompletedOperations = qa.destroyInputQueue();
                if (qa.getChannel() != null) {
                    qa.getChannel().close();
                    qa.setSk(null);
                    if (qa.getBytesRemainingToWrite() > 0) {
                        getLogger().warn(
                                "Shut down with %d bytes remaining to write",
                                qa.getBytesRemainingToWrite());
                    }
                    getLogger().debug("Shut down channel %s", qa.getChannel());
                }
                redistributeOperations(notCompletedOperations);
            }
