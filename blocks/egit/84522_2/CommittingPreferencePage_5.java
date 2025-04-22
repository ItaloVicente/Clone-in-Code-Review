		BooleanFieldEditor autoStageDeletion = new BooleanFieldEditor(
				GitCorePreferences.core_autoStageDeletion,
				UIText.CommittingPreferencePage_autoStageDeletion,
				generalGroup) {

			@Override
			public IPreferenceStore getPreferenceStore() {
				return corePreferences;
			}
		};
		addField(autoStageDeletion);

