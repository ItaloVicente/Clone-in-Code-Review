
		BooleanFieldEditor includeUntracked = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED,
				UIText.CommittingPreferencePage_includeUntrackedFiles, main);
		includeUntracked.getDescriptionControl(main).setToolTipText(
				UIText.CommittingPreferencePage_includeUntrackedFilesTooltip);
		addField(includeUntracked);

		IntegerFieldEditor historySize = new IntegerFieldEditor(
				UIPreferences.COMMIT_DIALOG_HISTORY_SIZE,
				UIText.CommittingPreferencePage_commitMessageHistory, main);
		addField(historySize);
