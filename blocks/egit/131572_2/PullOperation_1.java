					new PullJob(repositories[i], configs.get(repositories[i]))
						.schedule();
				}

				try {
					Job.getJobManager().join(
							PullOperation.class.getSimpleName(), progress);
				} catch (OperationCanceledException | InterruptedException e) {
					throw new CoreException(Status.CANCEL_STATUS);
