	public int getQueueLength() {
		int total = 0;
		for (int i = 0; i < loadLocks.length; i++) {
			total += loadLocks[i].getQueueLength();
		}
		return total;
	}

