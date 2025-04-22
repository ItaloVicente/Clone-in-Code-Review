		try {
			if (getProjectsInRepositoryOfSelectedResources(null).length == 0)
				return false;

			IResource[] resources = getSelectedResources(null);
			for (IResource resource : resources) {
				if (!Team.isIgnoredHint(resource))
					return true;
			}
			return false;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
