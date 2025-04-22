						IPath path2 = m.getGitDirAbsolutePath();
						if (path2 != null) {
							TreeItem ti = new TreeItem(treeItem, SWT.NONE);
							updateProjectTreeItem(ti, project);
							fillTreeItemWithGitDirectory(m, ti, path, true);
							ti.setData(new ProjectAndRepo(
									m.getContainer().getProject(),
									path2.toOSString()));
						}
					}
					if (treeItem != null) {
						treeItem.setExpanded(true);
