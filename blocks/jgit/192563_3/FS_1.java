		static {
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					SAVE_RUNNER.shutdownNow();
					SAVE_RUNNER.awaitTermination(100
				} catch (Exception e) {}
			}));
		}

