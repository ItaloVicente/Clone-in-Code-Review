			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					SAVE_RUNNER.shutdownNow();
					SAVE_RUNNER.awaitTermination(100, TimeUnit.MILLISECONDS);
				} catch (Exception e) {
				}
			}));
