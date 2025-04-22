	@SuppressWarnings("restriction")
	public boolean addCSSClass(Widget widget, String className) {
		CSSEngine engine = WidgetElement.getEngine(widget);
		String cssClasses = (String) widget
				.getData(CSSSWTConstants.CSS_CLASS_NAME_KEY);

		if (cssClasses == null || engine == null) {
			return false;
		}
		if (cssClasses.contains(className)) {
			return true;
		}

		widget.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY,
				String.format("%s %s", cssClasses, className));

		engine.applyStyles(widget, false);

		return true;
	}

	@SuppressWarnings("restriction")
	public boolean removeCSSClass(Widget widget, String className) {
		CSSEngine engine = WidgetElement.getEngine(widget);
		String cssClasses = (String) widget
				.getData(CSSSWTConstants.CSS_CLASS_NAME_KEY);

		if (cssClasses == null || engine == null) {
			return false;
		}
		if (!cssClasses.contains(className)) {
			return true;
		}

		widget.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY,
				cssClasses.replaceFirst(" " + className, ""));

		engine.applyStyles(widget, false);

		return true;
	}

