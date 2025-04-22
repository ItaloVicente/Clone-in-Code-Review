			fontResetButton.setEnabled(!isDefault && !colorDefinition.isOverridden()
					&& isAvailableInCurrentTheme(colorDefinition));
			editDefaultButton.setEnabled(hasDefault && isDefault && !colorDefinition.isOverridden()
					&& isAvailableInCurrentTheme(colorDefinition));
			goToDefaultButton.setEnabled(hasDefault && !colorDefinition.isOverridden()
					&& isAvailableInCurrentTheme(colorDefinition));
