	@Override
	public void reset() {
		super.reset();
		Composite composite = getComposite();

		if (composite.getData(BACKGROUND_OVERRIDDEN_BY_CSS_MARKER) != null) {
			composite.setData(BACKGROUND_OVERRIDDEN_BY_CSS_MARKER, null);
		}
	}

	public static boolean hasBackgroundOverriddenByCSS(Control control) {
		return control.getData(BACKGROUND_OVERRIDDEN_BY_CSS_MARKER) != null;
	}

	public static void setBackgroundOverriddenByCSSMarker(Widget widget) {
		if (widget instanceof Composite && !(widget instanceof CTabFolder)) {
			widget.setData(BACKGROUND_OVERRIDDEN_BY_CSS_MARKER, true);
		}
	}

