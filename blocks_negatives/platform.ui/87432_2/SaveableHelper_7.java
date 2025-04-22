		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				progressOp.run(monitor);
				if (!monitor.isCanceled())
					success[0] = true;
			}
