					fillTreeItemWithGitDirectory(m, treeItem);
				}

				while (mi.hasNext()) {
					RepositoryMapping m = mi.next();
					TreeItem treeItem2 = new TreeItem(treeItem, SWT.NONE);
					fillTreeItemWithGitDirectory(m, treeItem2);
