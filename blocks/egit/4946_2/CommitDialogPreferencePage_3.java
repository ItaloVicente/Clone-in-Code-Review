
		BooleanFieldEditor includeUntracked = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED,
				UIText.CommitDialogPreferencePage_includeUntrackedFiles, main);
		includeUntracked.getDescriptionControl(main).setToolTipText(
				UIText.CommitDialogPreferencePage_includeUntrackedFilesTooltip);
		addField(includeUntracked);
