								break;
							case INTERACTIVE_PREPARED:
								PlatformUI.getWorkbench().getDisplay()
										.asyncExec(() -> {
											try {
												RebaseInteractiveView view = (RebaseInteractiveView) PlatformUI
														.getWorkbench()
														.getActiveWorkbenchWindow()
														.getActivePage()
														.showView(
																RebaseInteractiveView.VIEW_ID);
												view.setInput(repository);
											} catch (PartInitException e) {
												Activator.logError(
														e.getMessage(), e);
											}
										});
								break;
							default:
