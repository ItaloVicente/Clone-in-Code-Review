		IRendererFactory factory = e4Context.get(IRendererFactory.class);
		AbstractPartRenderer obj = factory.getRenderer(mToolBar, null);
		if (obj instanceof ToolBarManagerRenderer) {
			ToolBarManagerRenderer renderer = (ToolBarManagerRenderer) obj;
			renderer.linkModelToManager(mToolBar, toolbarManager);
		}
