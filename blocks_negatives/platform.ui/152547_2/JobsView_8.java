					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InterruptedException {
							if (shouldLock)
								doRunInWorkspace(duration, monitor);
							else
								doRun(duration, monitor);
						}
					}, ResourcesPlugin.getWorkspace().getRoot());
