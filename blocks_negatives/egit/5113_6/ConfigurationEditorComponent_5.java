		Object selected = ((IStructuredSelection) tv.getSelection())
				.getFirstElement();

		boolean entrySelected = selected instanceof Entry;
		boolean sectionOrSubSectionSelected = (selected instanceof Section || selected instanceof SubSection);

		if (entrySelected)
			valueText.setText(((Entry) selected).value);
		else
			valueText
					.setText(UIText.ConfigurationEditorComponent_NoEntrySelectedMessage);
		changeValue.setEnabled(false);
		valueText.setEnabled(entrySelected);
		valueText.setEditable(editable && entrySelected);
		deleteValue.setEnabled(editable && entrySelected);
		addValue.setEnabled(editable && entrySelected);
		remove.setEnabled(editable && sectionOrSubSectionSelected);
