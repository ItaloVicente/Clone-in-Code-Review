				getPreferenceStore().setValue(
						UIPreferences.STAGING_SHOW_NEW_COMMITS, isChecked());
			}
		};
		openNewCommitsAction.setChecked(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_SHOW_NEW_COMMITS));

		fileNameModeAction = new Action(UIText.StagingView_ShowFileNamesFirst,
				IAction.AS_CHECK_BOX) {

			public void run() {
				final boolean enable = isChecked();
				getLabelProvider(stagedTableViewer).setFileNameMode(enable);
				getLabelProvider(unstagedTableViewer).setFileNameMode(enable);
				stagedTableViewer.refresh();
				unstagedTableViewer.refresh();
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_FILENAME_MODE, enable);
