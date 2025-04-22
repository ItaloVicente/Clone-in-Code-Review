	@SuppressWarnings("restriction")
	protected void reapplyStyles(Widget widget) {
		CSSEngine engine = WidgetElement.getEngine(widget);
		if (engine != null) {
			engine.reapply();
		}
	}

