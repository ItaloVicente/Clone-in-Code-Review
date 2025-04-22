		Text text = getTextControl();
		if (text != null) {
			int value = getPreferenceStore().getInt(getPreferenceName());
			text.setText("" + value);//$NON-NLS-1$
			oldValue = "" + value; //$NON-NLS-1$
		}
