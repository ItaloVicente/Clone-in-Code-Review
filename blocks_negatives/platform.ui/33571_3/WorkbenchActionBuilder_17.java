        saveAllAction = ActionFactory.SAVE_ALL.create(window);
        register(saveAllAction);
		
        newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(getWindow());
        newWindowAction.setText(IDEWorkbenchMessages.Workbench_openNewWindow);
        register(newWindowAction);
