	private final MemcachedConnection conn;
	private final AuthDescriptor authDescriptor;
	private final OperationFactory opFact;
	private final MemcachedNode node;

	public AuthThread(MemcachedConnection c, OperationFactory o,
			AuthDescriptor a, MemcachedNode n) {
		conn = c;
		opFact = o;
		authDescriptor = a;
		node = n;
		start();
	}

	@Override
	public void run() {
		OperationStatus priorStatus = null;
		final AtomicBoolean done = new AtomicBoolean();

		while(!done.get()) {
			final CountDownLatch latch = new CountDownLatch(1);
			final AtomicReference<OperationStatus> foundStatus =
				new AtomicReference<OperationStatus>();

			final OperationCallback cb=new OperationCallback() {
				public void receivedStatus(OperationStatus val) {
					if(val.getMessage().length() == 0) {
						done.set(true);
						node.authComplete();
						getLogger().info("Authenticated to "
								+ node.getSocketAddress());
					} else {
						foundStatus.set(val);
					}
				}

				public void complete() {
					latch.countDown();
				}
			};

			final Operation op = buildOperation(priorStatus, cb);

			conn.insertOperation(node, op);

			try {
				latch.await();
				Thread.sleep(100);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				if (op != null) {
					op.cancel();
				}
			}

			priorStatus = foundStatus.get();
			if(priorStatus != null) {
				if(!priorStatus.isSuccess()) {
					getLogger().warn("Authentication failed to "
							+ node.getSocketAddress());
				}
			}
		}
		return;
	}

	private Operation buildOperation(OperationStatus st, OperationCallback cb) {
		if(st == null) {
			return opFact.saslAuth(authDescriptor.mechs,
					node.getSocketAddress().toString(), null,
					authDescriptor.cbh, cb);
		} else {
			return opFact.saslStep(authDescriptor.mechs,
					KeyUtil.getKeyBytes(st.getMessage()),
					node.getSocketAddress().toString(), null,
					authDescriptor.cbh, cb);
		}

	}
