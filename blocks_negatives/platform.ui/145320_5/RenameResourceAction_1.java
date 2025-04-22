						PlatformUI
								.getWorkbench()
								.getOperationSupport()
								.getOperationHistory()
								.execute(
										op,
										monitor,
										WorkspaceUndoUtil
												.getUIInfoAdapter(getShell()));
					} catch (ExecutionException e) {
