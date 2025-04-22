		return button;
	}

	/**
	 * Return whether or not we should apply the workaround where we take focus for
	 * the default button or if that should be determined by the dialog. By default
	 * only return true if the custom area is a label or CLabel that cannot take
	 * focus.
	 *
	 * @return boolean
	 */
	protected boolean customShouldTakeFocus() {
		if (customArea instanceof Label) {
