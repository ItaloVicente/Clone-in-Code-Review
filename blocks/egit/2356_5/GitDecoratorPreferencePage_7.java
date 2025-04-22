		}

		public void initializeValues(IPreferenceStore store) {
			changeSetLabelFormat.initializeValue(store);
			dateFormat.setText(store.getString(UIPreferences.DATE_FORMAT));
		}

		public void performDefaults(IPreferenceStore store) {
			changeSetLabelFormat.performDefaults(store);
			dateFormat.setText(store.getDefaultString(UIPreferences.DATE_FORMAT));
		}

		public void performOk(IPreferenceStore store) {
			changeSetLabelFormat.performOk(store);
