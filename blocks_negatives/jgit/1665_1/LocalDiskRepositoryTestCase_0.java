		if (shutdownHook == null) {
			shutdownHook = new Thread() {
				@Override
				public void run() {
					System.gc();
					recursiveDelete("SHUTDOWN", trash, false, false);
				}
			};
			Runtime.getRuntime().addShutdownHook(shutdownHook);
