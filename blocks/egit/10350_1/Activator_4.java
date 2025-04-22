		if (ignoreDerivedResourcesListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					ignoreDerivedResourcesListener);
			ignoreDerivedResourcesListener = null;
		}
		if (shareGitProjectsJob != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					shareGitProjectsJob);
			shareGitProjectsJob = null;
		}
