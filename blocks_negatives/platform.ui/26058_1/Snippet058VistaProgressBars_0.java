			dialog.run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {

					IProgressMonitorWithBlocking blocking = (IProgressMonitorWithBlocking) monitor;

					blocking.beginTask("Vista Coolness", 100);
					for (int i = 0; i < 10; i++) {
						blocking.setBlocked(new Status(IStatus.WARNING,
								"Blocked", "This is blocked on Vista"));
						blocking.worked(5);
						spin(dialog.getShell().getDisplay());
						blocking.clearBlocked();
						blocking.worked(5);
						spin(dialog.getShell().getDisplay());
						if (monitor.isCanceled())
							return;
					}
					blocking.done();
				}
			});
