	private static Boolean useNativeSearchField;

	private static boolean useNativeSearchField(Composite composite) {
		if (useNativeSearchField == null) {
			useNativeSearchField = Boolean.FALSE;
			Text testText = null;
			try {
				testText = new Text(composite, SWT.SEARCH | SWT.ICON_CANCEL);
				useNativeSearchField = Boolean.valueOf(
						(testText.getStyle() & SWT.ICON_CANCEL) != 0);
			} finally {
				if (testText != null) {
					testText.dispose();
				}
			}

		}
		return useNativeSearchField.booleanValue();
	}

