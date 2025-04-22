		try {
			Job.getJobManager().join(JobFamilies.REPO_VIEW_REFRESH,
					new TimeoutProgressMonitor(60, TimeUnit.SECONDS));
		} catch (OperationCanceledException e) {
			fail("Refresh took longer 60 seconds");
		}
