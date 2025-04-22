
	private ToolBarManagerRenderer getToolBarManagerRenderer() {
		if (toolBarManagerRenderer == null) {
			toolBarManagerRenderer = context.get(ToolBarManagerRenderer.class);
		}
		return toolBarManagerRenderer;
	}
