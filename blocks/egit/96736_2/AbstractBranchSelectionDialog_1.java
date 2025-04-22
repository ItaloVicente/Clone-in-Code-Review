	private RefNode createRefNode(RepositoryTreeNode<?> root,
			Repository repository, Ref ref) {
		IContentProvider cp = branchTree.getContentProvider();
		if (cp instanceof RepositoriesViewContentProvider
				&& ((RepositoriesViewContentProvider) cp).isHierarchical()) {
			String fullName = ref.getName();
			int i = 0;
			if (fullName.startsWith(Constants.R_HEADS)) {
				i = Constants.R_HEADS.length();
			} else if (fullName.startsWith(Constants.R_REMOTES)) {
				i = Constants.R_REMOTES.length();
			}
			RepositoryTreeNode<?> top = root;
			int l = fullName.length();
			while (i < l) {
				int j = fullName.indexOf('/', i);
				if (j > i) {
					top = new BranchHierarchyNode(top, repository,
							new Path(fullName.substring(0, j)));
					i = j + 1;
				} else {
					break;
				}
			}
			return new RefNode(top, repository, ref);
		} else {
			return new RefNode(root, repository, ref);
		}
	}

