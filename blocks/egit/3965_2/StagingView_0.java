		columnLayoutAction.setChecked(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_COLUMN_LAYOUT));

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
			}
		};
		fileNameModeAction.setChecked(getPreferenceStore().getBoolean(
				UIPreferences.STAGING_VIEW_FILENAME_MODE));

		IMenuManager dropdownMenu = getViewSite().getActionBars()
				.getMenuManager();
		dropdownMenu.add(openNewCommitsAction);
		dropdownMenu.add(columnLayoutAction);
		dropdownMenu.add(fileNameModeAction);
	}
