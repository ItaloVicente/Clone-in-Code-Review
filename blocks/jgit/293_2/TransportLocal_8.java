
			if (errorReaderThread != null) {
				try {
					errorReaderThread.join();
				} catch (InterruptedException e) {
				} finally {
					errorReaderThread = null;
				}
			}
