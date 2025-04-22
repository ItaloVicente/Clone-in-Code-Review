			if (errorThread != null) {
				try {
					errorThread.join();
				} catch (InterruptedException e) {
				} finally {
					errorThread = null;
				}
			}

