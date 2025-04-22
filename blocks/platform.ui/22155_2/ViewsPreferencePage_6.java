		colorsAndFontsThemeCombo.setContentProvider(new ArrayContentProvider());
		colorsAndFontsThemeCombo.setInput(getColorsAndFontsThemes());
		colorsAndFontsThemeCombo.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		colorsAndFontsThemeCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ColorsAndFontsTheme colorsAndFontsTheme = getSelectedColorsAndFontsTheme();
				refreshColorsAndFontsThemeDescriptionText(colorsAndFontsTheme);
				setColorsAndFontsTheme(colorsAndFontsTheme);
			}
		});		
