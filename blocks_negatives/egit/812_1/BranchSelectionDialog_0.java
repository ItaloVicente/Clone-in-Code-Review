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
