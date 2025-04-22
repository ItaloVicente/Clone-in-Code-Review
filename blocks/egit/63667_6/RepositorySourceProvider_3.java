		try {
			service.run(false, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					while (!monitor.isCanceled() && updateInProgress) {
