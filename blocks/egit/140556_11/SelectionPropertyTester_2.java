		if (property == null){
			return false;
		}
		switch (property) {
		case "projectsSingleRepository": //$NON-NLS-1$
			return testRepositoryProperties(getRepositoryOfProjects(collection,
					true), args);
		case "projectsWithRepositories": //$NON-NLS-1$
			return getRepositoryOfProjects(collection,false) != null;
		case "selectionSingleRepository": //$NON-NLS-1$
			return SelectionUtils.getRepository(getStructuredSelection(collection)) != null;
		case "resourcesMultipleRepositories": //$NON-NLS-1$
			return resourceSelectionContainsMoreThanOneRepository(collection, args);
		case "selectionMultipleRepositories": //$NON-NLS-1$
			return selectionContainsMoreThanOneRepository(collection, args);
		case "resourcesSingleRepository": //$NON-NLS-1$
		{
			IStructuredSelection selection = getStructuredSelection(
					collection);
