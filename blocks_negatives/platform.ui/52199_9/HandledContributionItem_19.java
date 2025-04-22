		return model;
	}

	private ToolItemUpdater getUpdater() {
		if (model != null) {
			Object obj = model.getRenderer();
			if (obj instanceof ToolBarManagerRenderer) {
				return ((ToolBarManagerRenderer) obj).getUpdater();
			}
		}
		return null;
