			final ListSelectionDialog dialog = new ListSelectionDialog(text
					.getShell(), bindings.entrySet(), contentsProvider,
					labelProvider,
					UIText.DecoratorPreferencesPage_selectVariablesToAdd);
			dialog.setHelpAvailable(false);
			dialog
			.setTitle(UIText.DecoratorPreferencesPage_addVariablesTitle);
			if (dialog.open() != Window.OK)
				return;
