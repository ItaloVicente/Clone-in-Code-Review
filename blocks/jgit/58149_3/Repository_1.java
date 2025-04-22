	public void closeForcibly() {
		synchronized (useCnt) {
			useCnt.set(0);
		}
		doClose();
	}

