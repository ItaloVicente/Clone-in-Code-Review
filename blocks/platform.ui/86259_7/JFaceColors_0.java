	public static Color getInformationViewerBackgroundColor(Display display) {
		if (Util.isWin32() || Util.isCocoa()) {
			return display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		}

		return display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	}

	public static Color getInformationViewerForegroundColor(Display display) {
		if (Util.isWin32() || Util.isCocoa()) {
			return display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
		}

		return display.getSystemColor(SWT.COLOR_LIST_FOREGROUND);
	}
