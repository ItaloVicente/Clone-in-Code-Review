			if (outThread != null) {
				try {
					outThread.halt();
				} catch (InterruptedException e) {
				} finally {
					outThread = null;
				}
			}
