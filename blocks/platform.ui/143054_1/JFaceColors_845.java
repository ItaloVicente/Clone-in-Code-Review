	public static Color getBannerBackground(Display display) {
		return display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	}

	public static Color getBannerForeground(Display display) {
		return display.getSystemColor(SWT.COLOR_LIST_FOREGROUND);
	}

	public static Color getErrorBackground(Display display) {
		return display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
	}

	public static Color getErrorBorder(Display display) {
		return display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW);
	}

	public static Color getErrorText(Display display) {
		return JFaceResources.getColorRegistry().get(
				JFacePreferences.ERROR_COLOR);
	}

