					IPath path = m.getGitDirAbsolutePath();
					if (path != null) {
						TreeItem treeItem = new TreeItem(tree, SWT.NONE);
						updateProjectTreeItem(treeItem, project);
						treeItem.setText(1, project.getLocation().toOSString());
						fillTreeItemWithGitDirectory(m, treeItem, path, false);
						treeItem.setData(
								new ProjectAndRepo(project, path.toOSString()));
						treeItem.setChecked(true);
					}
