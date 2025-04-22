
	private IEclipseContext findContext(MApplication app) {
		MWindow window = app.getSelectedElement();
		if (window == null && !app.getChildren().isEmpty()) {
			window = app.getChildren().get(0);
		}
		return window != null ? window.getContext() : null;
	}
