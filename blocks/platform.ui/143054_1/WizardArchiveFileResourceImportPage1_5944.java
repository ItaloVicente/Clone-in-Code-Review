			sourceNames = addToHistory(sourceNames, sourceNameField.getText());
			settings.put(STORE_SOURCE_NAMES_ID, sourceNames);
			settings.put(STORE_OVERWRITE_EXISTING_RESOURCES_ID,
					overwriteExistingResourcesCheckbox.getSelection());
		}
	}
