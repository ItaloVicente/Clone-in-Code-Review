        fontChangeButton.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent event) {
            	Display display = event.display;
            	if (isFontSelected())
            		editFont(display);
            	else if (isColorSelected())
            		editColor(display);
            	updateControls();
            }
        });

        fontResetButton.addSelectionListener(new SelectionAdapter() {

            @Override
			public void widgetSelected(SelectionEvent e) {
            	if (isFontSelected())
					resetFont(getSelectedFontDefinition(), false);
            	else if (isColorSelected())
					resetColor(getSelectedColorDefinition(), false);
            	updateControls();
            }
        });

        fontSystemButton.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent event) {
                FontDefinition definition = getSelectedFontDefinition();
                if (definition == null)
                	return;
                FontData[] defaultFontData = JFaceResources.getDefaultFont().getFontData();
				setFontPreferenceValue(definition, defaultFontData, false);
                updateControls();
            }
        });
