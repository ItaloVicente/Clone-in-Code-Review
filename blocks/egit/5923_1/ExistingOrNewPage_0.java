	private TreeItem createProjectTreeItem(IProject project) {
		TreeItem treeItem = new TreeItem(tree, SWT.NONE);
		treeItem.setImage(0,
				PlatformUI.getWorkbench().getSharedImages()
						.getImage(SharedImages.IMG_OBJ_PROJECT));
		treeItem.setText(0, project.getName());
		return treeItem;
	}

