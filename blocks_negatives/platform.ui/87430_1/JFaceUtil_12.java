		SafeRunnable.setRunner(new ISafeRunnableRunner() {
			@Override
			public void run(ISafeRunnable code) {
				SafeRunner.run(code);
			}
		});
