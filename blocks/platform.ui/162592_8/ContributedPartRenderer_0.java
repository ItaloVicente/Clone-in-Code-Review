	@Override
	public void postProcess(MUIElement element) {
		super.postProcess(element);

		if (!(element instanceof MPart)) {
			return;
		}

		MToolBar toolbar = ((MPart) element).getToolbar();
		if (toolbar != null) {
			engine.createGui(toolbar);
		}
	}

