							if (operation == Operation.ABORT) {
								setAmending(false, false);

							}
							if (rebase.getResult().getStatus() == Status.EDIT) {
								setAmending(true, true);
							}
						}
					}

					private void setAmending(final boolean amending,
							final boolean openStagingView) {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								try {
									IViewPart view;
									if (openStagingView)
										view = PlatformUI.getWorkbench()
												.getActiveWorkbenchWindow()
												.getActivePage()
												.showView(StagingView.VIEW_ID);
									else
										view = PlatformUI
											.getWorkbench()
											.getActiveWorkbenchWindow()
											.getActivePage()
											.findView(StagingView.VIEW_ID);
									if (view instanceof StagingView) {
										final StagingView sv = (StagingView) view;
										sv.reload(repository);
										Display.getDefault().asyncExec(
												new Runnable() {
													public void run() {
														sv.setAmending(amending);
													}
												});
									}
								} catch (PartInitException e) {
									Activator.logError(e.getMessage(),
											e);
								}
							}
						});
