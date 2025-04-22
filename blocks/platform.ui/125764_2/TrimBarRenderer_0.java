		List<MTrimElement> children = element.getChildren();
		for (MTrimElement mTrimElement : children) {
			if (mTrimElement instanceof ToolBarImpl) {
				ToolBarImpl tb = (ToolBarImpl) mTrimElement;
				releaseToolbar(rendererFactory, tb);
			}
		}
	}

	private void releaseToolbar(IRendererFactory rendererFactory, ToolBarImpl tb) {
		AbstractPartRenderer apr = rendererFactory.getRenderer(tb, null);
		if (apr instanceof ToolBarManagerRenderer) {
			ToolBarManagerRenderer tbmr = (ToolBarManagerRenderer) apr;
			ToolBarManager tbm = tbmr.getManager(tb);
			tbmr.clearModelToManager(tb, null);
			if (tbm != null) {
				tbm.dispose();
			}
		}
