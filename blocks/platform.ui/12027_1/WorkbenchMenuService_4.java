		MPart modelPart = getPartToExtend(toolbarManager.getControl());
		if (modelPart != null) {
			mToolBar = modelPart.getToolbar();
		} else {
			System.err.println("Can not register a ToolBarManager without a MPart"); //$NON-NLS-1$
		}
		if (mToolBar == null) {
			mToolBar = MenuFactoryImpl.eINSTANCE.createToolBar();
			mToolBar.setElementId(location.getPath());
		}
		if (modelPart != null) {
			modelPart.setToolbar(mToolBar);
		}

		IRendererFactory factory = e4Context.get(IRendererFactory.class);
		AbstractPartRenderer obj = factory.getRenderer(mToolBar, null);
		if (obj instanceof ToolBarManagerRenderer) {
			ToolBarManagerRenderer renderer = (ToolBarManagerRenderer) obj;
			renderer.linkModelToManager(mToolBar, toolbarManager);
		}

		return mToolBar;
