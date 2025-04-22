						treeItem2 = new TreeItem(treeItem, SWT.NONE);
						updateProjectTreeItem(treeItem2, project);
						fillTreeItemWithGitDirectory(m, treeItem2, true);
						treeItem2.setData(new ProjectAndRepo(m.getContainer()
								.getProject(), m.getGitDirAbsolutePath()
								.toOSString()));
