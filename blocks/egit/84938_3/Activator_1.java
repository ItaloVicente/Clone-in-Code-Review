		public void stop() {
			boolean isRunning = !checkProjectsJob.cancel();
			Job.getJobManager().cancel(JobFamilies.AUTO_SHARE);
			try {
				if (isRunning) {
					checkProjectsJob.join();
				}
				Job.getJobManager().join(JobFamilies.AUTO_SHARE,
						new NullProgressMonitor());
			} catch (OperationCanceledException e) {
			} catch (InterruptedException e) {
				logError(e.getLocalizedMessage(), e);
			}
		}

