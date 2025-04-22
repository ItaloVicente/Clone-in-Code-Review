	private boolean selectionContainMoreThanOneRepository(
			Collection<?> collection, Object[] args) {
		IStructuredSelection selection = getStructuredSelection(collection);

		IResource[] resources = SelectionUtils.getSelectedResources(selection);
		Set<Repository> repositories = Stream.of(resources) //
				.map(SelectionPropertyTester::getRepositoryOfMapping) //
				.collect(Collectors.toSet());

		if (repositories.size() < 2) {
			return false;
		}

		return repositories.stream().allMatch(
				r -> SelectionPropertyTester.testRepositoryProperties(r, args));
	}

