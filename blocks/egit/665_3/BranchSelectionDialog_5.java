	private boolean markRef(String refName) {
		if (refName == null)
			return false;
		Ref actRef;
		try {
			actRef = repo.getRef(refName);
		} catch (IOException e) {
			return false;
		}

		if (actRef == null)
			return false;

		RepositoryTreeNode<Repository> parentNode;
		if (refName.startsWith(Constants.R_HEADS)) {
			parentNode = localBranches;
		} else if (refName.startsWith(Constants.R_REMOTES)) {
			parentNode = remoteBranches;
		} else if (refName.startsWith(Constants.R_TAGS)) {
			parentNode = tags;
		} else {
			return false;
		}

		RepositoryTreeNode<Ref> actNode = new RepositoryTreeNode<Ref>(
				parentNode, RepositoryTreeNodeType.REF, repo, actRef);
		branchTree.setSelection(new StructuredSelection(actNode), true);
		return true;
