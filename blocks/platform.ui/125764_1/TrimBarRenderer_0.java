		List<MTrimElement> children = element.getChildren();
		IRendererFactory rendererFactory = context.get(IRendererFactory.class);
		for (MTrimElement mTrimElement : children) {
			if (mTrimElement instanceof ToolBarImpl) {
				ToolBarImpl tb = (ToolBarImpl) mTrimElement;
				AbstractPartRenderer apr = rendererFactory.getRenderer(tb, null);
				if (apr instanceof ToolBarManagerRenderer) {
					ToolBarManagerRenderer tbmr = (ToolBarManagerRenderer) apr;
					ToolBarManager tbm = tbmr.getManager(tb);
					tbmr.clearModelToManager(tb, null);
					if (tbm != null) {
						tbm.dispose();
					}
				}
			}
		}
