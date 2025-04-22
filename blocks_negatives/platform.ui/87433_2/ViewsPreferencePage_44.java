		colorsAndFontsThemeCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ColorsAndFontsTheme colorsAndFontsTheme = getSelectedColorsAndFontsTheme();
				if (!colorsAndFontsTheme.equals(currentColorsAndFontsTheme)) {
					Image decorationImage = FieldDecorationRegistry.getDefault()
							.getFieldDecoration(FieldDecorationRegistry.DEC_WARNING).getImage();
					colorFontsDecorator.setImage(decorationImage);
					colorFontsDecorator
							.setDescriptionText(WorkbenchMessages.ThemeChangeWarningText);
					colorFontsDecorator.show();
				} else
					colorFontsDecorator.hide();
				refreshColorsAndFontsThemeDescriptionText(colorsAndFontsTheme);
				setColorsAndFontsTheme(colorsAndFontsTheme);
			}
