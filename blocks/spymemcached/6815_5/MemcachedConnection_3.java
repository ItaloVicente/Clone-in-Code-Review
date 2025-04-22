		retryOps = new ArrayList<Operation>();
		nodesToShutdown = new ConcurrentLinkedQueue<MemcachedNode>();
		this.bufSize = bufSize;
		this.connectionFactory = f;
		List<MemcachedNode> connections = createConnections(a);
		locator=f.createLocator(connections);
		}
