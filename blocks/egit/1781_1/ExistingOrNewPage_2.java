				else {
					TreeItem treeItem = new TreeItem(tree, SWT.NONE);
					treeItem.setText(0, project.getName());
					treeItem.setText(1, project.getLocation().toOSString());
					treeItem.setData(new ProjectAndRepo(null, null));

