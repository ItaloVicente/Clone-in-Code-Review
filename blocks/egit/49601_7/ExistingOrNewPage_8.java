						fillTreeItemWithGitDirectory(m, treeItem2, path, true);
						treeItem2.setData(
								new ProjectAndRepo(project,
								path.toOSString()));
						while (mi.hasNext()) { // fill in additional mappings
							m = mi.next();
							path = m.getGitDirAbsolutePath();
							if(path != null){
								treeItem2 = new TreeItem(treeItem, SWT.NONE);
								updateProjectTreeItem(treeItem2, project);
								fillTreeItemWithGitDirectory(m, treeItem2, path, true);
								treeItem2.setData(new ProjectAndRepo(m.getContainer()
										.getProject(), path.toOSString()));
							}
						}
						treeItem.setExpanded(true);
