		SshPreferencesMirror.INSTANCE.stop();
		if (preferenceChangeListener != null) {
			InstanceScope.INSTANCE.getNode(PLUGIN_ID)
					.removePreferenceChangeListener(preferenceChangeListener);
			preferenceChangeListener = null;
		}
		SshSessionFactory current = SshSessionFactory.getInstance();
		if (current instanceof SshdSessionFactory) {
			((SshdSessionFactory) current).close();
		}
		if (proxyServiceTracker != null) {
			proxyServiceTracker.close();
			proxyServiceTracker = null;
		}
		if (mergeStrategyRegistryListener != null) {
			Platform.getExtensionRegistry()
					.removeListener(mergeStrategyRegistryListener);
			mergeStrategyRegistryListener = null;
		}
		if (preDeleteProjectListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(preDeleteProjectListener);
			preDeleteProjectListener = null;
		}
		if (ignoreDerivedResourcesListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					ignoreDerivedResourcesListener);
			ignoreDerivedResourcesListener.stop();
			ignoreDerivedResourcesListener = null;
		}
		if (refreshHandle != null) {
			refreshHandle.remove();
			refreshHandle = null;
		}
		if (shareGitProjectsJob != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					shareGitProjectsJob);
			shareGitProjectsJob.stop();
			shareGitProjectsJob = null;
		}
		GitProjectData.detachFromWorkspace();
		indexDiffCache.dispose();
		indexDiffCache = null;
		repositoryCache.clear();
		repositoryCache = null;
		repositoryUtil.dispose();
		repositoryUtil = null;
		secureStore = null;
