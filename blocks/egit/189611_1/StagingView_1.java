		showUntracked = getPreferenceStore()
				.getBoolean(UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED);
		showUntrackedAction = new Action(UIText.StagingView_ShowUntrackedFiles,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				showUntracked = isChecked();
				unstagedViewer.refresh();
			}
		};
		showUntrackedAction.setImageDescriptor(UIIcons.UNTRACKED_FILE);
		showUntrackedAction.setId(SHOW_UNTRACKED_TOOLBAR_ID);
		showUntrackedAction.setChecked(showUntracked);

