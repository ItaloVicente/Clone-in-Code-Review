					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InterruptedException {
							Job.getJobManager().join(
									ResourcesPlugin.FAMILY_AUTO_REFRESH,
									new NullProgressMonitor());
						}
					});
