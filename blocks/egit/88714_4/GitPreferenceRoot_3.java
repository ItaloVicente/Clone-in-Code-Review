
		IntegerFieldEditor intervalField = new IntegerFieldEditor(
				UIPreferences.REFESH_INDEX_INTERVAL,
				UIText.RefreshPreferencesPage_RefreshIndexInterval,
				repoChangeScannerGroup);
		intervalField.getLabelControl(repoChangeScannerGroup).setToolTipText(
				UIText.RefreshPreferencesPage_RefreshIndexIntervalTooltip);
		addField(intervalField);
