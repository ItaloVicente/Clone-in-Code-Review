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

	private void setInitialFilterText() {
		filterText.setText(IDEWorkbenchMessages.CleanDialog_typeFilterText);
		filterText.selectAll();
		filterText.setFocus();
	}

	protected void showClearButton(boolean visible) {
		if (clearLabel != null) {
			clearLabel.setVisible(visible);
			GridData layoutData = (GridData) clearLabel.getLayoutData();
			layoutData.exclude = !visible;
			clearLabel.getParent().requestLayout();
		}
	}

