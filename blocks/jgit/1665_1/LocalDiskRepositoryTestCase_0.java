		synchronized(this) {
			if (shutdownHook == null) {
				shutdownHook = new Thread() {
					@Override
					public void run() {
						System.gc();
						recursiveDelete("SHUTDOWN"
					}
				};
				Runtime.getRuntime().addShutdownHook(shutdownHook);
			}
