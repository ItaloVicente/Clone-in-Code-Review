	private IRunnableWithProgress getRunnableForJob(final Job j) {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InterruptedException {
				JobMonitor jobMonitor = ProgressManager.getInstance().progressFor(j);
				jobMonitor.addProgressListener(getJobListeningMonitor(monitor));
				j.join();
			}
		};
		return runnable;
	}

	private IProgressMonitorWithBlocking getJobListeningMonitor(final IProgressMonitor monitor) {
		IProgressMonitorWithBlocking listener = new IProgressMonitorWithBlocking() {
			@Override
			public void beginTask(String name, int totalWork) {
				monitor.beginTask(name, totalWork);
			}

			@Override
			public void done() {
				monitor.done();
			}

			@Override
			public void internalWorked(double work) {
				monitor.internalWorked(work);
			}

			@Override
			public boolean isCanceled() {
				return monitor.isCanceled();
			}

			@Override
			public void setCanceled(boolean value) {
				monitor.setCanceled(value);
			}

			@Override
			public void setTaskName(String name) {
				monitor.setTaskName(name);
			}

			@Override
			public void subTask(String name) {
				monitor.subTask(name);
			}

			@Override
			public void worked(int work) {
				monitor.worked(work);
			}

			@Override
			public void setBlocked(IStatus reason) {
				if (monitor instanceof IProgressMonitorWithBlocking) {
					((IProgressMonitorWithBlocking) monitor).setBlocked(reason);
				}
			}

			@Override
			public void clearBlocked() {
				if (monitor instanceof IProgressMonitorWithBlocking) {
					((IProgressMonitorWithBlocking) monitor).clearBlocked();
				}
			}
		};
		return listener;
	}

