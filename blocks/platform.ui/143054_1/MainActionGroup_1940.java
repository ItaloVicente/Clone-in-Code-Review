					getNavigator().getSite().getShell().getDisplay().syncExec(() -> {
						addTaskAction.selectionChanged(selection);
						gotoGroup.updateActionBars();
						refactorGroup.updateActionBars();
						workspaceGroup.updateActionBars();
					});
				}
			}
		}
	}

	@Override
