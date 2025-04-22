		colorsAndFontsThemeCombo = new ComboViewer(composite, SWT.READ_ONLY);
		colorsAndFontsThemeCombo.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false));
		colorsAndFontsThemeCombo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ColorsAndFontsTheme) element).getLabel();
