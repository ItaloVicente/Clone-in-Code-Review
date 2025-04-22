		compareModeAction = new Action(UIText.StagingView_CompareMode,
				IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_COMPARE_MODE, isChecked());
			}
		};
		compareModeAction.setImageDescriptor(UIIcons.ELCL16_COMPARE_VIEW);
		compareModeAction.setChecked(getPreferenceStore()
				.getBoolean(UIPreferences.STAGING_VIEW_COMPARE_MODE));

		toolbar.add(compareModeAction);
		toolbar.add(new Separator());

