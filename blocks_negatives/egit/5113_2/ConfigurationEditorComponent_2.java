		if (entrySelected)
			valueText.setText(((Entry) selected).value);
		else
			valueText
					.setText(UIText.ConfigurationEditorComponent_NoEntrySelectedMessage);
		changeValue.setEnabled(false);
		valueText.setEnabled(entrySelected);
		valueText.setEditable(editable && entrySelected);
		deleteValue.setEnabled(editable && entrySelected);
