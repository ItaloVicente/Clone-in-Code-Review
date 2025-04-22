	private void selectAndReveal(
			Map<Repository, Collection<String>> pathsByRepo) {
		final List<RepositoryTreeNode> nodesToShow = new ArrayList<>();
		for (Map.Entry<Repository, Collection<String>> entry : pathsByRepo
				.entrySet()) {
			Repository repository = entry.getKey();
