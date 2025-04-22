					getContainer().run(true, true, new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException {
							getWizard().expandArchive(selection, monitor);
							if (monitor.isCanceled()) {
								throw new InterruptedException();
							}
