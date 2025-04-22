		try {
			Job.getJobManager().join(WORKBENCH_AUTO_SAVE_JOB, new NullProgressMonitor());
		} catch (OperationCanceledException e) {
		} catch (InterruptedException e) {
		}

