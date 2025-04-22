					treeItem.setData(
							new ProjectAndRepo(project, "")); //$NON-NLS-1$
				} else if (!mi.hasNext()){
					TreeItem treeItem = new TreeItem(tree, SWT.NONE);
					treeItem.setText(0, project.getName());
					treeItem.setText(1, project.getLocation().toOSString());
