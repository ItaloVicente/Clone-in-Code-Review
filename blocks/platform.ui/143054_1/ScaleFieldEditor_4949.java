		if (scale != null) {
			int value = getPreferenceStore().getDefaultInt(getPreferenceName());
			scale.setSelection(value);
		}
		valueChanged();
	}

	@Override
