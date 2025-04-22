		if (ref.length() == lck.length()) {
			long otime = ref.lastModified();
			long ntime = lck.lastModified();
			while (otime == ntime) {
				Thread.sleep(25 /* milliseconds */);
				lck.setLastModified(System.currentTimeMillis());
				ntime = lck.lastModified();
			}
