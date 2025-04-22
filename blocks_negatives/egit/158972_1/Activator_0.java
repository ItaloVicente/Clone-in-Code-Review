		if (shareGitProjectsJob != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					shareGitProjectsJob);
			shareGitProjectsJob.stop();
			shareGitProjectsJob = null;
		}
