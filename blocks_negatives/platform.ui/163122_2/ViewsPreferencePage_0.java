	/**
	 * Create the text box that will contain the current theme description text (if
	 * any).
	 *
	 * @param parent the parent <code>Composite</code>.
	 */
	private void createColorsAndFontsThemeDescriptionText(Composite parent) {
		new Label(parent, SWT.NONE).setText(WorkbenchMessages.ViewsPreference_currentThemeDescription);

		colorsAndFontsThemeDescriptionText = new Text(parent,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER | SWT.WRAP);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		GC gc = new GC(parent);
		layoutData.heightHint = Dialog.convertHeightInCharsToPixels(gc.getFontMetrics(), 2);
		gc.dispose();
		colorsAndFontsThemeDescriptionText.setLayoutData(layoutData);
	}

