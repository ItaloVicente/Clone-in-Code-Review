	private boolean getInitialMRUValue(Control control) {
		CSSRenderingUtils util = context.get(CSSRenderingUtils.class);
		if (util == null) {
			return getMRUValueFromPreferences();
		}

		CSSValue value = util.getCSSValue(control, "MPartStack", "swt-mru-visible"); //$NON-NLS-1$ //$NON-NLS-2$

		if (value == null) {
			value = util.getCSSValue(control, "MPartStack", "mru-visible"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (value == null) {
			return getMRUValueFromPreferences();
		}
		return Boolean.parseBoolean(value.getCssText());
	}

