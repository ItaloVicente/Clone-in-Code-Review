										}
									}
									monitor.worked(1);

									monitor.done();
								}

								private void updateViewBars(IViewPart part) {
									IViewSite viewSite = part.getViewSite();
									if (viewSite == null) {
										return;
									}
									IActionBars bars = viewSite.getActionBars();
									IContributionManager manager = bars.getMenuManager();
									if (manager != null) {
										updateManager(manager);
									}
									manager = bars.getToolBarManager();
									if (manager != null) {
										updateManager(manager);
									}
									manager = bars.getStatusLineManager();
									if (manager != null) {
										updateManager(manager);
									}
								}

								private void updateWindowBars(final WorkbenchWindow window) {
									IContributionManager manager = window.getMenuBarManager();
									if (manager != null) {
										updateManager(manager);
									}
									manager = window.getCoolBarManager2();
									if (manager != null) {
										updateManager(manager);
									}
									manager = window.getToolBarManager2();
									if (manager != null) {
										updateManager(manager);
									}
									manager = window.getStatusLineManager();
									if (manager != null) {
										updateManager(manager);
									}
								}

								private void updateManager(final IContributionManager manager) {
									if (!dialogOpened && System.currentTimeMillis() > openTime) {
										dialog.open();
										dialogOpened = true;
									}

									manager.update(true);
								}
							};

							dialog.setOpenOnRun(false);
							workbench.getDisplay()
									.asyncExec(() -> BusyIndicator.showWhile(workbench.getDisplay(), () -> {
										try {
											dialog.run(false, false, runnable);
										} catch (InvocationTargetException e1) {
											log(e1);
										} catch (InterruptedException e2) {
											log(e2);
										}
									}));
						}
					}
				}
			}

			private void log(Exception e) {
				StatusUtil.newStatus(IStatus.ERROR, "Could not update contribution managers", e); //$NON-NLS-1$
			}
		});
	}

	@Override
