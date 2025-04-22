				IRunnableWithProgress runnable = new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						monitor.beginTask(
								UIText.RefSpecDialog_GettingRemoteRefsMonitorMessage,
								IProgressMonitor.UNKNOWN);
						lop.run(monitor);
						monitor.done();
					}
				};
				if (shell != null) {
					new ProgressMonitorDialog(shell).run(true, true, runnable);
				} else {
					container.run(true, true, runnable);
				}
