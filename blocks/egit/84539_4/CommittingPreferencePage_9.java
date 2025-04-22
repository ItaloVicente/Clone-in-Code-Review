		BooleanFieldEditor autoStageMoves = new BooleanFieldEditor(
				GitCorePreferences.core_autoStageMoves,
				UIText.CommittingPreferencePage_autoStageMoves, generalGroup) {

			@Override
			public IPreferenceStore getPreferenceStore() {
				return corePreferences;
			}
		};
		addField(autoStageMoves);

