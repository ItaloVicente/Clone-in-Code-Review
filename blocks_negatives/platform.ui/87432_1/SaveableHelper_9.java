			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InterruptedException {
					Job.getJobManager().join(new DynamicFamily(modelsToSave), monitor);
				}
			});
