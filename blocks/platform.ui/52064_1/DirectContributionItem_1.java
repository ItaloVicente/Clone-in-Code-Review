
	public MItem getModel() {
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
	}

	protected void updateItemEnablement() {
		if (!(model.getWidget() instanceof ToolItem))
			return;

		ToolItem widget = (ToolItem) model.getWidget();
		if (widget == null || widget.isDisposed())
			return;

		SafeRunner.run(getUpdateRunner());
	}

