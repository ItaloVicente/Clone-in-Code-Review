		TreeSelection treeSelection = (TreeSelection) selection;
		for (Iterator iterator = treeSelection.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			if (!(object instanceof RepositoryTreeNode)) {
				setBaseEnabled(false);
				return;
			}
			Repository nodeRepository = ((RepositoryTreeNode) object)
					.getRepository();
