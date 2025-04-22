					treeItem2.setData(new ProjectAndRepo(
							project, treeItem2.getText(2)));
					while (mi.hasNext()) {	// fill in additional mappings
						m = mi.next();
						treeItem2 = new TreeItem(treeItem, SWT.NONE);
						treeItem2.setText(0, project.getName());
						fillTreeItemWithGitDirectory(m, treeItem2, true);
						treeItem2.setData(new ProjectAndRepo(m.getContainer()
								.getProject(), treeItem2.getText(2)));
					}
					treeItem.setExpanded(true);
