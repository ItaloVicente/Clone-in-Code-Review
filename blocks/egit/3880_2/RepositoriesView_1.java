	public void showRepository(Repository repositoryToShow) {
		ITreeContentProvider cp = (ITreeContentProvider) getCommonViewer()
				.getContentProvider();
		for (Object repo : cp.getElements(getCommonViewer().getInput())) {
			RepositoryTreeNode node = (RepositoryTreeNode) repo;
			if (repositoryToShow.getDirectory().equals(node.getRepository().getDirectory()))
				selectReveal(new StructuredSelection(node));
		}
	}
