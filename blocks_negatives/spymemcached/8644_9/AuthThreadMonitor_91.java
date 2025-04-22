	private void interruptOldAuth(MemcachedNode nodeToStop) {
		AuthThread toStop = nodeMap.get(nodeToStop);
		if (toStop != null) {
			if (toStop.isAlive()) {
				getLogger().warn("Incomplete authentication interrupted for node " +
					nodeToStop);
				toStop.interrupt();
			}
