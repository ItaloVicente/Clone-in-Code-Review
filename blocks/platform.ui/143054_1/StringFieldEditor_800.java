		if (textField != null) {
			String value = getPreferenceStore().getDefaultString(
					getPreferenceName());
			textField.setText(value);
		}
		valueChanged();
	}

	@Override
