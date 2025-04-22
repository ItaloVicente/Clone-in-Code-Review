					TreeItem treeItem = new TreeItem(tree, SWT.NONE);
					updateProjectTreeItem(treeItem, project);
					treeItem.setText(1, project.getLocation().toOSString());
					treeItem.setData(new ProjectAndRepo(project, "")); //$NON-NLS-1$

					TreeItem treeItem2 = new TreeItem(treeItem, SWT.NONE);
					updateProjectTreeItem(treeItem2, project);
					fillTreeItemWithGitDirectory(m, treeItem2, true);
					treeItem2.setData(new ProjectAndRepo(project, m
							.getGitDirAbsolutePath().toOSString()));
						m = mi.next();
						treeItem2 = new TreeItem(treeItem, SWT.NONE);
