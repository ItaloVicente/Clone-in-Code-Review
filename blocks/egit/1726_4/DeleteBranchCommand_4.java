								for (RefNode refNode : nodes) {
									int result = deleteBranch(refNode,
											refNode.getObject());
									if (result == DeleteBranchOperation.REJECTED_CURRENT) {
										throw new CoreException(
												Activator
														.createErrorStatus(
																UIText.DeleteBranchCommand_CannotDeleteCheckedOutBranch,
																null));
									} else if (result == DeleteBranchOperation.REJECTED_UNMERGED) {
										throw new CoreException(
												Activator
														.createErrorStatus(
																UIText.DeleteBranchCommand_UnmergedData,
																null));
									}
								}
							} catch (CoreException ex) {
								throw new InvocationTargetException(ex);
