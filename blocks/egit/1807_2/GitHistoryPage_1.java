		boolean filtersActive = inResources != null || inFiles != null;
		showAllRepoVersionsAction.setEnabled(filtersActive);
		showAllProjectVersionsAction.setEnabled(filtersActive);
		showAllFolderVersionsAction.setEnabled(inResources != null);
		showAllResourceVersionsAction.setEnabled(filtersActive);

		try {
			initAndStartRevWalk(true);
		} catch (IllegalStateException e) {
			Activator.handleError(e.getMessage(), e.getCause(), true);
			return false;
		}

		return true;
	}
