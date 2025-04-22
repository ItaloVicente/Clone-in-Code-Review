		Repository repository = refNode.getRepository();
		BranchRenameDialog branchRenameDialog = new BranchRenameDialog(shell,
				repository, refNode.getObject());
		branchRenameDialog.open();
		String newName = branchRenameDialog.getNewName();
		if (newName != null) {
			RepositoriesView repoView = getView(event);
			repoView.selectRevealRef(repository, Constants.R_HEADS + newName);
		}
