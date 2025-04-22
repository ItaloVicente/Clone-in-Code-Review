            sourceNames = addToHistory(sourceNames, sourceNameField.getText());
            settings.put(STORE_SOURCE_NAMES_ID, sourceNames);

            String[] selectedTypesNames = settings
                    .getArray(STORE_SELECTED_TYPES_ID);
            if (selectedTypesNames == null) {
				selectedTypesNames = new String[0];
			}

            settings.put(STORE_OVERWRITE_EXISTING_RESOURCES_ID,
                    overwriteExistingResourcesCheckbox.getSelection());
        }
    }
