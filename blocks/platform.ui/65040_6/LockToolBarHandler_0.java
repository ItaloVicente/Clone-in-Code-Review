		WorkbenchWindow window = (WorkbenchWindow) HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MTrimmedWindow winModel = window.getService(MTrimmedWindow.class);
		EModelService modelService = window.getService(EModelService.class);

		if (window != null) {
			ICoolBarManager coolBarManager = window.getCoolBarManager2();
