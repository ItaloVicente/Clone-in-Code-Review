
		final IRendererFactory factory = e4Context.get(IRendererFactory.class);
		final AbstractPartRenderer obj = factory.getRenderer(((WorkbenchWindow) getWindow())
				.getModel().getMainMenu(), null);
		if (!(obj instanceof MenuManagerRenderer)) {
			return null;
