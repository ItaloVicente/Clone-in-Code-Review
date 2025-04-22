	private void markRef(String refName) {
		if (refName != null) {
			Ref actRef;
			try {
				actRef = repo.getRef(refName);
			} catch (IOException e) {
				return;
			}
			if (actRef != null) {
				RepositoryTreeNode<Repository> parentNode;
				if (refName.startsWith(Constants.R_HEADS)) {
					parentNode = new RepositoryTreeNode<Repository>(null,
							RepositoryTreeNodeType.LOCALBRANCHES, repo, repo);
				} else if (refName.startsWith(Constants.R_REMOTES)) {
					parentNode = new RepositoryTreeNode<Repository>(null,
							RepositoryTreeNodeType.REMOTEBRANCHES, repo, repo);
				} else if (refName.startsWith(Constants.R_TAGS)) {
					parentNode = new RepositoryTreeNode<Repository>(null,
							RepositoryTreeNodeType.TAGS, repo, repo);
				} else {
					parentNode = null;
				}
				if (parentNode != null) {
					RepositoryTreeNode<Ref> actNode = new RepositoryTreeNode<Ref>(
							parentNode, RepositoryTreeNodeType.REF, repo,
							actRef);
					branchTree.setSelection(new StructuredSelection(actNode),
							true);
				}
			}
		}
