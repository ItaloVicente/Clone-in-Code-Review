	public void waitForStatChange() throws InterruptedException {
		if (ref.length() == lck.length()) {
			long otime = ref.lastModified();
			long ntime = lck.lastModified();
			while (otime == ntime) {
				lck.setLastModified(System.currentTimeMillis());
				ntime = lck.lastModified();
			}
		}
	}

