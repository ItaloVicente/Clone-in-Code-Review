		editDefaultButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Display display = event.display;
				FontDefinition fontDefinition = getSelectedFontDefinition();
				if (fontDefinition != null) {
					String defaultFontId = fontDefinition.getDefaultsTo();
					FontDefinition defaultFontDefinition = themeRegistry.findFont(defaultFontId);
					editFont(defaultFontDefinition, display);
				} else {
					ColorDefinition colorDefinition = getSelectedColorDefinition();
					if (colorDefinition != null) {
						String defaultColorId = colorDefinition.getDefaultsTo();
						ColorDefinition defaultColorDefinition = themeRegistry
								.findColor(defaultColorId);
						editColor(defaultColorDefinition, display);
					}
