				logError(e);
				fail.set(true);
			}
			try {
				is.close();
			} catch (IOException e) {
				logError(e);
				fail.set(true);
