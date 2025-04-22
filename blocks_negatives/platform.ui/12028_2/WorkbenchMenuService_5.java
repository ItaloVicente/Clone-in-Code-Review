	
		IRendererFactory factory = e4Context.get(IRendererFactory.class);
		AbstractPartRenderer obj = factory.getRenderer(mMenu, null);
		if (obj instanceof MenuManagerRenderer) {
			MenuManagerRenderer renderer = (MenuManagerRenderer) obj;
			renderer.linkModelToManager(mMenu, menuManager);
		}
	
