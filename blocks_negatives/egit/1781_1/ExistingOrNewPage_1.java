		final TreeItem[] selection = tree.getSelection();
		Map<IProject, File> ret = new HashMap<IProject, File>(selection.length);
		for (int i = 0; i < selection.length; ++i) {
			TreeItem treeItem = selection[i];
			while (treeItem.getData() ==  null && treeItem.getParentItem() != null) {
				treeItem = treeItem.getParentItem();
			}

			final IProject project = (IProject) treeItem.getData();
			final IPath selectedRepo = Path.fromOSString(treeItem.getText(2));
