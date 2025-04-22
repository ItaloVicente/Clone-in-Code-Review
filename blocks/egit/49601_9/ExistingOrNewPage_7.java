					IPath path = m.getGitDirAbsolutePath();
					if (path != null) {
						TreeItem treeItem = new TreeItem(tree, SWT.NONE);
						updateProjectTreeItem(treeItem, project);
						treeItem.setText(1, project.getLocation().toOSString());
						treeItem.setData(new ProjectAndRepo(project, "")); //$NON-NLS-1$

						TreeItem treeItem2 = new TreeItem(treeItem, SWT.NONE);
