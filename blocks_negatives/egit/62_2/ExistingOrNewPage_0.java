					if (m.getGitDir() == null)
						treeItem.setText(2,UIText.ExistingOrNewPage_SymbolicValueEmptyMapping);
					else
						treeItem.setText(2, m.getGitDir());
					while (mi.hasNext()) {
						TreeItem treeItem2 = new TreeItem(treeItem, SWT.NONE);
						if (m.getGitDir() == null)
							treeItem2.setText(2,UIText.ExistingOrNewPage_SymbolicValueEmptyMapping);
						else
							treeItem2.setText(2,m.getGitDir());
					}
