		final AtomicBoolean cancelled = new AtomicBoolean(false);
		while (updateInProgress && !cancelled.get()) {
			try {
				service.run(false, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						if (monitor.isCanceled()) {
							cancelled.set(true);
							return;
						}
