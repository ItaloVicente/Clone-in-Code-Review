		themeIdCombo.addSelectionChangedListener(event -> {
			ITheme selection = getSelectedTheme();
			if (!selection.equals(currentTheme)) {
				themeComboDecorator.setDescriptionText(WorkbenchMessages.ThemeChangeWarningText);
				Image decorationImage = FieldDecorationRegistry.getDefault()
						.getFieldDecoration(FieldDecorationRegistry.DEC_WARNING).getImage();
				themeComboDecorator.setImage(decorationImage);
				themeComboDecorator.show();
			} else {
				themeComboDecorator.hide();
