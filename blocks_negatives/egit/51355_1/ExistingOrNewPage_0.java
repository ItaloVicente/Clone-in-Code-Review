					TreeItem treeItem = new TreeItem(tree, SWT.NONE);
					updateProjectTreeItem(treeItem, project);
					treeItem.setText(1, project.getLocation().toOSString());
					fillTreeItemWithGitDirectory(m, treeItem, false);
					treeItem.setData(new ProjectAndRepo(project, m
							.getGitDirAbsolutePath().toOSString()));
					treeItem.setChecked(true);
