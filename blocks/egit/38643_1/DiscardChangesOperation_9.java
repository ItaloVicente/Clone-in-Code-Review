
		for (Entry<Repository, Collection<String>> entry : pathsByRepository
				.entrySet()) {
			Repository repository = entry.getKey();
			Collection<String> paths = entry.getValue();

			try {
				discardChanges(repository, paths);
			} catch (GitAPIException e) {
				errorOccurred = true;
				Activator.logError(
						CoreText.DiscardChangesOperation_discardFailed, e);
			}
			monitor.worked(1);

			try {
				ProjectUtil.refreshRepositoryResources(repository, paths,
						new SubProgressMonitor(monitor, 1));
			} catch (CoreException e) {
				errorOccurred = true;
				Activator.logError(
						CoreText.DiscardChangesOperation_refreshFailed, e);
			}
