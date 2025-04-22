		themeIdCombo.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ITheme selection = getSelectedTheme();
				if (!selection.equals(currentTheme)) {
					themeComboDecorator.setDescriptionText(WorkbenchMessages.ThemeChangeWarningText);
					Image decorationImage = FieldDecorationRegistry.getDefault()
							.getFieldDecoration(FieldDecorationRegistry.DEC_WARNING).getImage();
					themeComboDecorator.setImage(decorationImage);
					themeComboDecorator.show();
				} else {
					themeComboDecorator.hide();
				}
				try {
					((PreferencePageEnhancer) Tweaklets.get(PreferencePageEnhancer.KEY))
							.setSelection(selection);
				} catch (SWTException e) {
					WorkbenchPlugin.log("Failed to set CSS preferences", e); //$NON-NLS-1$
				}
				selectColorsAndFontsTheme(getColorAndFontThemeIdByThemeId(selection.getId()));
