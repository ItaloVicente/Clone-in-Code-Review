					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InterruptedException {
							Job.getJobManager().join(TestJob.FAMILY_TEST_JOB,
									monitor);
						}
					});
