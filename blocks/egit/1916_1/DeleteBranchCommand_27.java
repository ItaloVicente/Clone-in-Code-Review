										unmergedNodes.add(refNode);
									}

									if (!unmergedNodes.isEmpty()) {
										MessageDialog messageDialog = new BranchMessageDialog(
												shell, unmergedNodes);
										if (messageDialog.open() == Window.OK) {
											for (RefNode node : unmergedNodes) {
												deleteBranch(node, node
														.getObject(), true);
											}
										}
