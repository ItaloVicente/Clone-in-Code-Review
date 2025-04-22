	private ToolBar getToolbarFrom(Object widget) {
		if (widget instanceof ToolBar) {
			return (ToolBar) widget;
		}
		if (widget instanceof Composite) {
			Composite intermediate = (Composite) widget;
			if (!intermediate.isDisposed()) {
				Control[] children = intermediate.getChildren();
				for (Control control : children) {
					if (control.getData() instanceof ToolBarManager) {
						return (ToolBar) control;
					}
				}
			}
