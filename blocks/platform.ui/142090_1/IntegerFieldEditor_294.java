		Text text = getTextControl();
		if (text != null) {
			int value = getPreferenceStore().getDefaultInt(getPreferenceName());
			text.setText("" + value);//$NON-NLS-1$
		}
		valueChanged();
	}

	@Override
