	public static void applyStyles(Widget widget,
			boolean applyStylesToChildNodes) {
		CSSEngine engine = getEngine(widget);
		if (engine != null) {
			engine.applyStyles(widget, applyStylesToChildNodes);
		}
	}

