	@SuppressWarnings("unchecked")
	private boolean checkForRepositoryChanges() {

		if (tv.getInput() == null)
			return false;

		final Set<Repository> reposToRefresh = new HashSet<Repository>();

		RepositoryListener listener = new RepositoryListener() {

			public void refsChanged(RefsChangedEvent e) {
				reposToRefresh.add(e.getRepository());
			}

			public void indexChanged(IndexChangedEvent e) {
				reposToRefresh.add(e.getRepository());
			}
		};

		List<RepositoryTreeNode<Repository>> input = (List<RepositoryTreeNode<Repository>>) tv
				.getInput();

		for (final RepositoryTreeNode<Repository> node : input) {

			Repository repository = node.getRepository();
			repository.addRepositoryChangedListener(listener);
			try {
				repository.scanForRepoChanges();
			} catch (IOException e1) {
			} finally {
				repository.removeRepositoryChangedListener(listener);
			}
		}

		return !reposToRefresh.isEmpty();
	}

