					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor) {
							if (shouldLock)
								doRunInWorkspace(duration, monitor);
							else
								doRun(duration, monitor);
						}

