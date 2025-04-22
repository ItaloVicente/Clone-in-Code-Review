		menuManager = actionBars.getMenuManager();

		if (menuManager.find(IWorkbenchActionConstants.MB_ADDITIONS) != null)
			menuManager.insertAfter(IWorkbenchActionConstants.MB_ADDITIONS, new Separator(WORKING_SET_ACTION_GROUP));
		else
			menuManager.add(new Separator(WORKING_SET_ACTION_GROUP));

		menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, selectWorkingSetAction);
		menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, clearWorkingSetAction);
		menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, editWorkingSetAction);
		menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, new Separator(START_SEPARATOR_ID));
		menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, mruList);
		menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, new Separator(SEPARATOR_ID));
	}
