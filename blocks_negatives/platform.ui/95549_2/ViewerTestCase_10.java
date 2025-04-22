		Policy.setLog(new ILogger(){
			@Override
			public void log(IStatus status) {
				fail(status.getMessage());
			}});
		SafeRunnable.setRunner(new ISafeRunnableRunner(){
			@Override
			public void run(ISafeRunnable code) {
				try {
					code.run();
				} catch(Throwable th) {
					throw new RuntimeException(th);
				}
			}});
