
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
