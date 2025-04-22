						new Runnable() {
							@Override
							public void run() {
								tree.setCheckedElements(treeContentProvider
										.getElements(tree.getInput()));
								setSubtreeChecked((IContainer) tree.getInput(),
										true, false);
							}
