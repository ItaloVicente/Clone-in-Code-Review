	private void updateProjectTreeItem(TreeItem item, IProject project) {
		item.setImage(0,
				PlatformUI.getWorkbench().getSharedImages()
						.getImage(SharedImages.IMG_OBJ_PROJECT));
		item.setText(0, project.getName());
	}

