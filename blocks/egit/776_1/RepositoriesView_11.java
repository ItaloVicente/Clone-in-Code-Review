		scheduledJob = job;
	}

	private boolean checkForRepositoryChanges(List<RepositoryNode> input) {
		CommonViewer tv = getCommonViewer();
		if (tv.getInput() == null)
			return false;

		if (input.isEmpty())
			return false;

		final Set<Repository> reposToRefresh = new HashSet<Repository>();

		RepositoryListener listener = new RepositoryListener() {

			public void refsChanged(RefsChangedEvent e) {
				reposToRefresh.add(e.getRepository());
			}

			public void indexChanged(IndexChangedEvent e) {
				reposToRefresh.add(e.getRepository());
