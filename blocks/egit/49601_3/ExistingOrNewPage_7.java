					IPath path = m.getGitDirAbsolutePath();
					TreeItem treeItem = null;
					if (path != null) {
						treeItem = new TreeItem(tree, SWT.NONE);
						updateProjectTreeItem(treeItem, project);
						treeItem.setText(1, project.getLocation().toOSString());
						treeItem.setData(new ProjectAndRepo(project, "")); //$NON-NLS-1$

						TreeItem treeItem2 = new TreeItem(treeItem, SWT.NONE);
						updateProjectTreeItem(treeItem2, project);
						fillTreeItemWithGitDirectory(m, treeItem2, path, true);
						treeItem2.setData(
								new ProjectAndRepo(project, path.toOSString()));
					}
