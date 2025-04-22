							try {
								monitor.beginTask(UIText.DeleteBranchCommand_DeletingBranchesProgress, nodes.size());
								for (RefNode refNode : nodes) {
									int result = deleteBranch(refNode, refNode
											.getObject(), false);
									if (result == DeleteBranchOperation.REJECTED_CURRENT) {
										throw new CoreException(
												Activator
														.createErrorStatus(
																UIText.DeleteBranchCommand_CannotDeleteCheckedOutBranch,
																null));
									} else if (result == DeleteBranchOperation.REJECTED_UNMERGED) {
										unmergedNodes.add(refNode);
									} else
										monitor.worked(1);
								}
								if (!unmergedNodes.isEmpty()) {
									MessageDialog messageDialog = new UnmergedBranchDialog<RefNode>(
											shell, unmergedNodes);
									if (messageDialog.open() == Window.OK) {
										for (RefNode node : unmergedNodes) {
											deleteBranch(node,
													node.getObject(), true);
											monitor.worked(1);
										}
									}
								}
							} catch (CoreException ex) {
								throw new InvocationTargetException(ex);
							} finally {
								monitor.done();
							}
