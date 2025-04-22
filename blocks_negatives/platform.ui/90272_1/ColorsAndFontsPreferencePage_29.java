		});

		goToDefaultButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				FontDefinition fontDefinition = getSelectedFontDefinition();
				if (fontDefinition != null) {
					String defaultFontId = fontDefinition.getDefaultsTo();
					FontDefinition defaultFontDefinition = themeRegistry.findFont(defaultFontId);
					selectAndReveal(defaultFontDefinition);
				} else {
					ColorDefinition colorDefinition = getSelectedColorDefinition();
					if (colorDefinition != null) {
						String defaultColorId = colorDefinition.getDefaultsTo();
						ColorDefinition defaultColorDefinition = themeRegistry
								.findColor(defaultColorId);
						selectAndReveal(defaultColorDefinition);
					}
