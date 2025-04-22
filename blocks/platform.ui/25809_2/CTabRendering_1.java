	private Control findToolbarContainer() {
		Object obj = parent.getData(TOOLBAR_CONTAINER);
		if (obj instanceof Control) {
			Control control = (Control) obj;
			if (!control.isDisposed()) {
				return control;
			}
		}

		for (Control child : parent.getChildren()) {
			if (child instanceof Composite) {
				for (Control subChild : ((Composite) child).getChildren()) {
					if (subChild instanceof ToolBar && subChild.isVisible()) {
						parent.setData(TOOLBAR_CONTAINER, child);
						return child;
					}
				}
			}
		}
		return null;
