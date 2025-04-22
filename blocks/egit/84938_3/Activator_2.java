		public void stop() {
			Job.getJobManager().cancel(JobFamilies.AUTO_IGNORE);
			try {
				Job.getJobManager().join(JobFamilies.AUTO_IGNORE,
						new NullProgressMonitor());
			} catch (OperationCanceledException e) {
			} catch (InterruptedException e) {
				logError(e.getLocalizedMessage(), e);
			}
		}
