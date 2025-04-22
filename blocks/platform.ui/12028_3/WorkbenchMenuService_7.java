	protected MToolBar getToolbarModel(MApplicationElement model, ToolBarManager toolbarManager,
			MenuLocationURI location) {
		final IRendererFactory factory = e4Context.get(IRendererFactory.class);
		final AbstractPartRenderer obj = factory.getRenderer(
				MenuFactoryImpl.eINSTANCE.createToolBar(), null);
		if (!(obj instanceof ToolBarManagerRenderer)) {
			return null;
