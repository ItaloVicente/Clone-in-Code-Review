								if (!unmergedBranches.isEmpty()) {
									MessageDialog messageDialog = new UnmergedBranchDialog<Ref>(
											shell, unmergedBranches);
									if (messageDialog.open() == Window.OK) {
										for (Ref node : unmergedBranches) {
											deleteBranch(repository, node, true);
											monitor.worked(1);
										}
									}
								}
