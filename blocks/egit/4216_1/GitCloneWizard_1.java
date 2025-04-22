		if (repositorySelection.isSingleRepo()) {
			return cloneDestination.isPageComplete()
					&& gerritConfiguration.isPageComplete();
		} else {
			return repositorySelection.isPageComplete();
		}
