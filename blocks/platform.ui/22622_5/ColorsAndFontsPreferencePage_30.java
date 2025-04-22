			fontChangeButton.setEnabled(!fontDefinition.isOverridden()
					&& isAvailableInCurrentTheme(fontDefinition));
			fontSystemButton.setEnabled(!fontDefinition.isOverridden()
					&& isAvailableInCurrentTheme(fontDefinition));
			fontResetButton.setEnabled(!isDefault && !fontDefinition.isOverridden()
					&& isAvailableInCurrentTheme(fontDefinition));
			editDefaultButton.setEnabled(hasDefault && isDefault && !fontDefinition.isOverridden()
					&& isAvailableInCurrentTheme(fontDefinition));
			goToDefaultButton.setEnabled(hasDefault && !fontDefinition.isOverridden()
					&& isAvailableInCurrentTheme(fontDefinition));
