	public static enum Status {
		UNKNOWN(UIText.CommitDialog_StatusUnknown),
		ASSUME_UNCHANGED(UIText.CommitDialog_StatusAssumeUnchaged),
		ADDED_INDEX_DIFF(UIText.CommitDialog_StatusAddedIndexDiff),
		ADDED(UIText.CommitDialog_StatusAdded),
		MODIFIED_INDEX_DIFF(UIText.CommitDialog_StatusModifiedIndexDiff),
		MODIFIED(UIText.CommitDialog_StatusModified),
		REMOVED_UNTRACKED(UIText.CommitDialog_StatusRemovedUntracked),
		UNTRACKED(UIText.CommitDialog_StatusUntracked),
		REMOVED(UIText.CommitDialog_StatusRemoved),
		REMOVED_NOT_STAGED(UIText.CommitDialog_StatusRemovedNotStaged),
		MODIFIED_NOT_STAGED(UIText.CommitDialog_StatusRemovedNotStaged);

		public String getText() {
			return myText;
		}

		private final String myText;

		private Status(String text) {
			myText = text;
		}
	}

