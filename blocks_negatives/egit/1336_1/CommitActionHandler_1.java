		try {
			return getProjectsInRepositoryOfSelectedResources(null).length > 0;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
			return false;
		}
