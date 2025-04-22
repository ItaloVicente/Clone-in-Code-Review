		MToolBar mToolBar = null;

		MPart modelPart = getPartToExtend(toolbarManager.getControl());
		if (modelPart != null) {
			mToolBar = modelPart.getToolbar();
		} else {
