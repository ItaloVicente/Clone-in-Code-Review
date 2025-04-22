		othersWorkingSetAction = new Action() {
			@Override
			public void run() {
				stateModel.setBooleanProperty(
						WorkingSetsContentProvider.SHOW_OTHERS_WORKING_SET,
						!stateModel.getBooleanProperty(WorkingSetsContentProvider.SHOW_OTHERS_WORKING_SET));

				structuredViewer.getControl().setRedraw(false);
				try {
					structuredViewer.refresh();
				} finally {
					structuredViewer.getControl().setRedraw(true);
				}
			}

			@Override
			public boolean isEnabled() {
				return stateModel.getBooleanProperty(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS);
			}
		};
		othersWorkingSetAction
				.setText(NLS.bind(WorkbenchNavigatorMessages.WorkingSetRootModeActionGroup_Show_Others_working_set,
						WorkbenchNavigatorMessages.workingSet_others));

		return new IAction[] { projectsAction, workingSetsAction, othersWorkingSetAction };
