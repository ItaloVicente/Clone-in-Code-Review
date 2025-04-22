						});
						pullJob.schedule();
					}
				}
				for (int i = 0; i < pullJobs.length; i++) {
					Job job = pullJobs[i];
					try {
						job.join();
					} catch (InterruptedException e) {
						throw new CoreException(Status.CANCEL_STATUS);
