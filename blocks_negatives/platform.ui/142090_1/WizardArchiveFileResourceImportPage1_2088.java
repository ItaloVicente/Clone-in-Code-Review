            sourceNames = addToHistory(sourceNames, sourceNameField.getText());
            settings.put(STORE_SOURCE_NAMES_ID, sourceNames);
            settings.put(STORE_OVERWRITE_EXISTING_RESOURCES_ID,
                    overwriteExistingResourcesCheckbox.getSelection());
        }
    }

    /**
     *	Answer a boolean indicating whether self's source specification
     *	widgets currently all contain valid values.
     */
    @Override
