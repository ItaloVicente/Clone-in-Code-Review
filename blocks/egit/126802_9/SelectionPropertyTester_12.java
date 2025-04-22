	private boolean resourceSelectionContainsMoreThanOneRepository(
			Collection<?> collection, Object[] args) {
		IStructuredSelection selection = getStructuredSelection(collection);
		IResource[] resources = SelectionUtils.getSelectedResources(selection);
		Set<Repository> repos = Stream.of(resources) //
				.map(SelectionPropertyTester::getRepositoryOfMapping) //
				.collect(Collectors.toSet());
		return testMultipleRepositoryProperties(repos, args);
	}

	private boolean selectionContainsMoreThanOneRepository(
			Collection<?> collection, Object[] args) {
		IStructuredSelection selection = getStructuredSelection(collection);
		Repository[] repos = SelectionUtils.getRepositories(selection);
		return testMultipleRepositoryProperties(Arrays.asList(repos), args);
	}

	private boolean testMultipleRepositoryProperties(
			Collection<Repository> repos, Object[] args) {
		if (repos.size() < 2)
			return false;

		return repos.stream().allMatch(
				r -> SelectionPropertyTester.testRepositoryProperties(r, args));
	}

