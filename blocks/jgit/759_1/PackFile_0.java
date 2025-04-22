		synchronized (readLock) {
			if (fd != null) {
				try {
					fd.close();
				} catch (IOException err) {
				}
				fd = null;
