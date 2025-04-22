	private static boolean useNativeSearchField(Composite composite) {
		boolean useNativeSearchField = true;
		Text testText = null;
		try {
			testText = new Text(composite, SWT.SEARCH | SWT.ICON_CANCEL);
			useNativeSearchField = Boolean.valueOf((testText.getStyle() & SWT.ICON_CANCEL) != 0);
		} finally {
			if (testText != null) {
				testText.dispose();
			}
		}
		return useNativeSearchField;
	}

