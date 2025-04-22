			Repository repository = getRepositoryOfProjects(collection, true);
			return testRepositoryProperties(repository, args);

			Repository repository = getRepositoryOfProjects(collection, false);
			return repository != null;

			return SelectionUtils
					.getRepository(getStructuredSelection(collection)) != null;

			return resourceSelectionContainsMoreThanOneRepository(collection,
					args);

			return selectionContainsMoreThanOneRepository(collection,
					args);

			IStructuredSelection selection = getStructuredSelection(collection);

			IResource[] resources = SelectionUtils
					.getSelectedResources(selection);
			Repository repository = getRepositoryOfResources(resources);
			return testRepositoryProperties(repository, args);

			if (collection.size() != 1)
				return false;
