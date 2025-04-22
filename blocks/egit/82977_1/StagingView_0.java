	private boolean shouldGiveFocusToCommitMessage() {
		Tree stagedTree = stagedViewer.getTree();
		IPreferenceStore uiPreferences = Activator.getDefault()
				.getPreferenceStore();
		return uiPreferences.getBoolean(UIPreferences.AUTO_STAGE_ON_COMMIT)
				&& stagedTree.getItemCount() > 0;
	}

