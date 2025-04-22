		return new Thread(() -> {
			ISafeRunnable runnable = new SafeRunnable() {
				@Override
				public void run() throws Exception {
					throw new RuntimeException("test exception " + ++count);
				}
			};
			SafeRunnable.run(runnable);
