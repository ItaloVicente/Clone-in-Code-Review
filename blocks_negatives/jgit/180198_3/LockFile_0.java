			try {
				os = new FileOutputStream(lck);
			} catch (IOException ioe) {
				unlock();
				throw ioe;
			}
