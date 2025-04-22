				if (!colorsAndFontsTheme.equals(currentColorsAndFontsTheme)) {
					Image decorationImage = FieldDecorationRegistry.getDefault()
							.getFieldDecoration(FieldDecorationRegistry.DEC_WARNING).getImage();
					colorFontsDecorator.setImage(decorationImage);
					colorFontsDecorator
							.setDescriptionText(WorkbenchMessages.ThemeChangeWarningText);
					colorFontsDecorator.show();
				} else
					colorFontsDecorator.hide();
