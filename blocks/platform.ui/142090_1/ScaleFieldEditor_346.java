		if (scale != null) {
			int value = getPreferenceStore().getInt(getPreferenceName());
			scale.setSelection(value);
			oldValue = value;
		}
	}

	@Override
