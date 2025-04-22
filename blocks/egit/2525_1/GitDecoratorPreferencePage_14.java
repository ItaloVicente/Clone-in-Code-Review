
		public void performOk(IPreferenceStore store) {
			store.setValue(key, text.getText());
		}

		public void performDefaults(IPreferenceStore store) {
			store.setToDefault(key);
			text.setText(store.getDefaultString(key));
		}

		public void initializeValue(IPreferenceStore store) {
			text.setText(store.getString(key));
		}
