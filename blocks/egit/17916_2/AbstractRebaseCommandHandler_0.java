							if (rebase.getResult().getStatus()
									.equals(Status.EDIT)) {
								Display.getDefault().asyncExec(new Runnable() {
									public void run() {
										try {
											IViewPart view = PlatformUI
													.getWorkbench()
													.getActiveWorkbenchWindow()
													.getActivePage()
													.showView(StagingView.VIEW_ID);
											if (view instanceof StagingView) {
												StagingView sv = (StagingView) view;
												sv.reload(repository, true);
											}
										} catch (PartInitException e) {
											Activator.logError(e.getMessage(),
													e);
										}
									}
								});
								}

							}
