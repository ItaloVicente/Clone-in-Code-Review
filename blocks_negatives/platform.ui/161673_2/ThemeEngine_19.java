	@Override
	public void applyStyles(Object widget, boolean applyStylesToChildNodes) {
		for (CSSEngine engine : cssEngines) {
			Element element = engine.getElement(widget);
			if (element != null) {
				engine.applyStyles(element, applyStylesToChildNodes);
