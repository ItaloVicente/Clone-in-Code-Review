        fontResetButton.addSelectionListener(widgetSelectedAdapter(e -> {
			if (isFontSelected())
				resetFont(getSelectedFontDefinition(), false);
			else if (isColorSelected())
				resetColor(getSelectedColorDefinition(), false);
			updateControls();
		}));

        fontSystemButton.addSelectionListener(widgetSelectedAdapter(event -> {
		    FontDefinition definition = getSelectedFontDefinition();
		    if (definition == null)
		    	return;
		    FontData[] defaultFontData = JFaceResources.getDefaultFont().getFontData();
			setFontPreferenceValue(definition, defaultFontData, false);
		    updateControls();
		}));

		editDefaultButton.addSelectionListener(widgetSelectedAdapter(event -> {
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
