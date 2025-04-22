                    getNavigator().getSite().getShell().getDisplay().syncExec(
                            () -> {
							    addTaskAction.selectionChanged(selection);
							    gotoGroup.updateActionBars();
							    refactorGroup.updateActionBars();
							    workspaceGroup.updateActionBars();
							});
                }
            }
        }
    }

    /**
     * Makes the actions contained directly in this action group.
     */
    @Override
