		String externalDiffToolPreference = DiffMergeSettings
				.getExternalDiffToolPreference();
		if (StringUtils.isEmptyOrNull(externalDiffToolPreference)) {
			Text warningText = new Text(diffGroup, SWT.READ_ONLY);
			warningText.setText(UIText.DiffPreferencePage_WarningMessage);
			warningText.setEditable(false);
		}
