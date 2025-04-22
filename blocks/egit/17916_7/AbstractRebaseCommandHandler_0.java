							if (operation == Operation.ABORT) {
								setAmending(false);

							}
							if (rebase.getResult().getStatus() == Status.EDIT) {
								setAmending(true);
							}
						}
					}

					private void setAmending(
							final boolean amending) {
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
										sv.setAmending(amending);
										sv.reload(repository);
									}
								} catch (PartInitException e) {
									Activator.logError(e.getMessage(),
											e);
								}
							}
						});
