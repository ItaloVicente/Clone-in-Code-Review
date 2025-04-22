	@SuppressWarnings("restriction")
	public void addCSSClass(Widget widget, String className) {
		CSSEngine engine = WidgetElement.getEngine(widget);
		String cssClasses = emptyIfNull((String) widget
				.getData(CSSSWTConstants.CSS_CLASS_NAME_KEY));

		if (engine == null || cssClasses.contains(className)) {
			return;
		}

		widget.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY,
				String.format("%s %s", cssClasses, className).trim());

		engine.reapply(); // used instead of applyStyle since the UI refreshing
	}

	@SuppressWarnings("restriction")
	public void removeCSSClass(Widget widget, String className) {
		CSSEngine engine = WidgetElement.getEngine(widget);
		String cssClasses = emptyIfNull((String) widget
				.getData(CSSSWTConstants.CSS_CLASS_NAME_KEY));

		if (engine == null) {
			return;
		}

		StringBuilder newCssClasses = new StringBuilder();
		boolean removed = false;
		for (String item : cssClasses.split("\\s")) {
			if (!item.equals(className)) {
				newCssClasses.append(newCssClasses.length() == 0 ? "" : " ")
						.append(item);
			} else {
				removed = true;
			}
		}

		if (removed) {
			widget.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY,
					nullIfEmpty(newCssClasses.toString()));

			engine.reapply();
		}
	}

