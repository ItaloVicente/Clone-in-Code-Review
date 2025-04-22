	private void fillTreeItemWithGitDirectory(RepositoryMapping m, TreeItem treeItem2) {
		if (m.getGitDir() == null)
			treeItem2.setText(2,UIText.ExistingOrNewPage_SymbolicValueEmptyMapping);
		else
			treeItem2.setText(2,m.getGitDir());
	}

