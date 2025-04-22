				BusyIndicator.showWhile(getShell().getDisplay(),
						() -> {
							tree.setCheckedElements(treeContentProvider
									.getElements(tree.getInput()));
							setSubtreeChecked((IContainer) tree.getInput(),
									true, false);
						});
