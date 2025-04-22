	/**
	 * Convenience method for requesting the CSS engine to re-apply styles to a
	 * widget.
	 *
	 * @param widget
	 *            widget to be restyled
	 * @param applyStylesToChildNodes
	 *            if true, apply styles to the child nodes
	 */
	public static void applyStyles(Widget widget,
			boolean applyStylesToChildNodes) {
		CSSEngine engine = getEngine(widget);
		if (engine != null) {
			engine.applyStyles(widget, applyStylesToChildNodes);
		}
	}

