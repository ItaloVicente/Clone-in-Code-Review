
		MenuItem toggleLockToolbars = new MenuItem(toolControlMenu, SWT.NONE);
		toggleLockToolbars.setText(getLockToolbarsText());
		toggleLockToolbars.addListener(SWT.Selection, event -> {
			EHandlerService handlerService = context.get(EHandlerService.class);
			ECommandService commandService = context.get(ECommandService.class);
			ParameterizedCommand pCommand = commandService.createCommand(LOCK_TOOLBAR_CMD_ID, Collections.EMPTY_MAP);
			handlerService.executeHandler(pCommand);
			toggleLockToolbars.setText(getLockToolbarsText());
		});
