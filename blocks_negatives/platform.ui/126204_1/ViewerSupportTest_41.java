		SafeRunnable.setRunner(new ISafeRunnableRunner() {
			@Override
			public void run(ISafeRunnable code) {
				try {
					code.run();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
