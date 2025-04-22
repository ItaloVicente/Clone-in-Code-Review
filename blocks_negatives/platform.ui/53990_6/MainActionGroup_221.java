                            new Runnable() {
                                @Override
								public void run() {
                                    addTaskAction.selectionChanged(selection);
                                    gotoGroup.updateActionBars();
                                    refactorGroup.updateActionBars();
                                    workspaceGroup.updateActionBars();
                                }
                            });
