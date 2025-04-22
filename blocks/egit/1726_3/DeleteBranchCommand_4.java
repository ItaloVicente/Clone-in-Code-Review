								for (RefNode refNode : nodes) {
									int result = deleteBranch(refNode, refNode
											.getObject());
									if (result == DeleteBranchOperation.REJECTED_CURRENT) {
										throw new CoreException(
												Activator
														.createErrorStatus(
																"Can not delete the currently checked out branch", null)); //TODO //$NON-NLS-1$
									} else if (result == DeleteBranchOperation.REJECTED_UNMERGED) {
										throw new CoreException(
												Activator
														.createErrorStatus(
																"Unmerged data, use force to delete anyway", null)); //TODO //$NON-NLS-1$
									}
								}
							} catch (CoreException ex) {
								throw new InvocationTargetException(ex);
