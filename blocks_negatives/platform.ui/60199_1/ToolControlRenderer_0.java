		if (((Object) toolControl.getParent()) instanceof MToolBar) {
			IRendererFactory factory = context.get(IRendererFactory.class);
			AbstractPartRenderer renderer = factory.getRenderer(
					toolControl.getParent(), parent);
			if (renderer instanceof ToolBarManagerRenderer) {
				return null;
			}
		}
