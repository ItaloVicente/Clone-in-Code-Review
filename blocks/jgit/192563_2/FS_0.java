		static {
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						SAVE_RUNNER.shutdownNow();
						SAVE_RUNNER.awaitTermination(100
					} catch (Exception e) {}
				}
			}));
		}

