	public void close() {
		if (useCnt.decrementAndGet() == 0) {
			objectDatabase.close();
			refs.close();
		}
	}
