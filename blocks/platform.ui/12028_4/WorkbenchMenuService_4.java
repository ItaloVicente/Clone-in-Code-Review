		MApplicationElement model = getPartToExtend();
		if (model == null) {
			final IWorkbenchWindow window = getWindow();
			if (window instanceof WorkbenchWindow) {
				model = ((WorkbenchWindow) window).getModel();
			}
