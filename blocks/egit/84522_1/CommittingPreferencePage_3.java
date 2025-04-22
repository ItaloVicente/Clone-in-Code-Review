		BooleanFieldEditor autoStageDeletion = new BooleanFieldEditor(
				GitCorePreferences.core_autoStageDeletion,
				UIText.CommittingPreferencePage_autoStageDeletion,
				generalGroup) {

			@Override
			public IPreferenceStore getPreferenceStore() {
				return corePreferences;
			}
		};
		autoStageDeletion.getDescriptionControl(generalGroup).setToolTipText(
				UIText.CommittingPreferencePage_autoStageDeletionTooltip);
		addField(autoStageDeletion);

