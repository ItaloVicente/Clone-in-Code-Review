                                            IActionBars bars = viewSite
                                                    .getActionBars();
                                            IContributionManager manager = bars
                                                    .getMenuManager();
                                            if (manager != null) {
												updateManager(manager);
											}
                                            manager = bars.getToolBarManager();
                                            if (manager != null) {
												updateManager(manager);
											}
                                            manager = bars
                                                    .getStatusLineManager();
                                            if (manager != null) {
												updateManager(manager);
											}
                                        }

                                        /**
                                         * Update the managers on the given window.
                                         *
                                         * @param window the window to update
                                         */
                                        private void updateWindowBars(
                                                final WorkbenchWindow window) {
                                            IContributionManager manager = window
                                                    .getMenuBarManager();
                                            if (manager != null) {
												updateManager(manager);
											}
                                            manager = window
                                                    .getCoolBarManager2();
                                            if (manager != null) {
												updateManager(manager);
											}
                                            manager = window
                                                    .getToolBarManager2();
                                            if (manager != null) {
												updateManager(manager);
											}
                                            manager = window
                                                    .getStatusLineManager();
                                            if (manager != null) {
												updateManager(manager);
											}
                                        }

                                        /**
                                         * Update the given manager in the UI thread.
                                         * This may also open the progress dialog if
                                         * the operation is taking too long.
                                         *
                                         * @param manager the manager to update
                                         */
                                        private void updateManager(
                                                final IContributionManager manager) {
                                            if (!dialogOpened
                                                    && System
                                                            .currentTimeMillis() > openTime) {
                                                dialog.open();
                                                dialogOpened = true;
                                            }

                                            manager.update(true);
                                        }
                                    };

                                    dialog.setOpenOnRun(false);
                                    workbench.getDisplay().asyncExec(
                                            () -> BusyIndicator
											        .showWhile(
											                workbench
											                        .getDisplay(),
											                () -> {
															    try {
															        dialog
															                .run(
															                        false,
															                        false,
															                        runnable);
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

                    /**
                     * Logs an error message to the workbench log.
                     *
                     * @param e the exception to log
                     */
                    private void log(Exception e) {
                        StatusUtil.newStatus(IStatus.ERROR,
                                "Could not update contribution managers", e); //$NON-NLS-1$
                    }
                });
    }

    @Override
