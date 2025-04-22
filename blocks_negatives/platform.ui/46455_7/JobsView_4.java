			IProgressService progressService = PlatformUI.getWorkbench()
					.getProgressService();
			progressService.runInUI(progressService,
					new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InterruptedException {
							if (shouldLock)
								doRunInWorkspace(duration, monitor);
							else
								doRun(duration, monitor);
						}
					}, ResourcesPlugin.getWorkspace().getRoot());
