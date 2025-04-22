		window.run(fork, cancelable, new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				monitor.beginTask("beginTask", 10);
				monitor.setTaskName("setTaskName");
				monitor.subTask("subTask");

				if (monitor instanceof IProgressMonitorWithBlocking) {
					IProgressMonitorWithBlocking blockingMonitor = (IProgressMonitorWithBlocking) monitor;
					blockingMonitor.setBlocked(Status.CANCEL_STATUS);
					blockingMonitor.clearBlocked();
				}
