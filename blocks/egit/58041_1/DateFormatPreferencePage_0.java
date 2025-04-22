	private void updateFields(String newSelection) {
		GitDateFormatter.Format format = fromString(newSelection);
		FormatInfo info = DATA.get(format);
		formatExplanation.setText(info.explanation);
		if (format == null) {
			dateFormat.getTextControl(getFieldEditorParent()).setEnabled(true);
			dateFormat.setStringValue(lastCustomValue);
		} else {
			dateFormat.getTextControl(getFieldEditorParent()).setEnabled(false);
			dateFormat.setStringValue(info.format);
			updatePreview(format);
		}
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		lastCustomValue = getPreferenceStore()
				.getDefaultString(UIPreferences.DATE_FORMAT);
		updateFields(getPreferenceStore()
				.getDefaultString(UIPreferences.DATE_FORMAT_CHOICE));
	}

