	private boolean selectionContainsRepositoryGroup(Collection<?> collection) {
		IStructuredSelection selection = getStructuredSelection(collection);
		return ((List<?>) selection.toList()).stream()
				.anyMatch(s -> s instanceof RepositoryGroupNode);
	}

