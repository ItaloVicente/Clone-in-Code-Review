		for (ViewerFilter filter : getCommonViewer().getFilters()) {
			if (NodeFilterFactory.isWorkTreeFilter(filter)) {
				ITreeContentProvider cp = (ITreeContentProvider) getCommonViewer()
						.getContentProvider();
				RepositoryTreeNode repoNode = findRepositoryNode(cp,
						cp.getElements(getCommonViewer().getInput()),
						repository);
				return repoNode;
			}
		}
