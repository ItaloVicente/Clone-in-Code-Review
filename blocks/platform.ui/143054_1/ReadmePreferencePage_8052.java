	}

	private void initializeDefaults() {
		IPreferenceStore store = getPreferenceStore();
		checkBox1.setSelection(store.getDefaultBoolean(IReadmeConstants.PRE_CHECK1));
		checkBox2.setSelection(store.getDefaultBoolean(IReadmeConstants.PRE_CHECK2));
		checkBox3.setSelection(store.getDefaultBoolean(IReadmeConstants.PRE_CHECK3));

		radioButton1.setSelection(false);
		radioButton2.setSelection(false);
		radioButton3.setSelection(false);
		int choice = store.getDefaultInt(IReadmeConstants.PRE_RADIO_CHOICE);
		switch (choice) {
		case 1:
			radioButton1.setSelection(true);
			break;
		case 2:
			radioButton2.setSelection(true);
			break;
		case 3:
			radioButton3.setSelection(true);
			break;
		}
		textField.setText(store.getDefaultString(IReadmeConstants.PRE_TEXT));
	}

	private void initializeValues() {
		IPreferenceStore store = getPreferenceStore();
		checkBox1.setSelection(store.getBoolean(IReadmeConstants.PRE_CHECK1));
		checkBox2.setSelection(store.getBoolean(IReadmeConstants.PRE_CHECK2));
		checkBox3.setSelection(store.getBoolean(IReadmeConstants.PRE_CHECK3));

		int choice = store.getInt(IReadmeConstants.PRE_RADIO_CHOICE);
		switch (choice) {
		case 1:
			radioButton1.setSelection(true);
			break;
		case 2:
			radioButton2.setSelection(true);
			break;
		case 3:
			radioButton3.setSelection(true);
			break;
		}
		textField.setText(store.getString(IReadmeConstants.PRE_TEXT));
	}

	@Override
