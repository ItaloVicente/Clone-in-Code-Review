			Job.getJobManager().join(GitCloneWizard.CLONE_JOB_FAMILY, new NullProgressMonitor());
		} catch (Exception e) {
			fail( "Unable to join cloning job");
		}
		for (int i = 0; i < 3; i++) {
			if (Activator.getDefault().getRepositoryUtil()
					.getConfiguredRepositories().contains(targetDir))
				return;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
