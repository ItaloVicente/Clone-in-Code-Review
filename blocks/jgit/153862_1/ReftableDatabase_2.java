	public boolean hasFastTipsWithSha1() throws IOException {
		lock.lock();
		try {
			return reader().hasObjectMap();
		} finally {
			lock.unlock();
		}
	}

