		if (getProjectsInRepositoryOfSelectedResources().length == 0)
			return false;

		IResource[] resources = getSelectedResources();
		for (IResource resource : resources) {
			if (!Team.isIgnoredHint(resource))
				return true;
		}
		return false;
