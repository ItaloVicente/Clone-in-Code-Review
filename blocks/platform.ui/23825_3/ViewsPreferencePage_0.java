				if (!selection.equals(currentTheme)) {
					themeComboDecorator.setDescriptionText(WorkbenchMessages.ThemeChangeWarningText);
					Image image = FieldDecorationRegistry.getDefault()
							.getFieldDecoration(FieldDecorationRegistry.DEC_WARNING).getImage();
					themeComboDecorator.setImage(image);
					themeComboDecorator.show();
				} else {
					themeComboDecorator.hide();
				}
