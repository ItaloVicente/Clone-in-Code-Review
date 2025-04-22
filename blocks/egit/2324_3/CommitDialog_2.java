	public static enum Status {
		ADDED(UIText.CommitDialog_StatusAdded),
		MODIFIED(UIText.CommitDialog_StatusModified),
		REMOVED(UIText.CommitDialog_StatusRemoved),
		ADDED_INDEX_DIFF(UIText.CommitDialog_StatusAddedIndexDiff),
		MODIFIED_INDEX_DIFF(UIText.CommitDialog_StatusModifiedIndexDiff),
		MODIFIED_NOT_STAGED(UIText.CommitDialog_StatusModifiedNotStaged),
		REMOVED_NOT_STAGED(UIText.CommitDialog_StatusRemovedNotStaged),
		UNTRACKED(UIText.CommitDialog_StatusUntracked),
		REMOVED_UNTRACKED(UIText.CommitDialog_StatusRemovedUntracked),
		ASSUME_UNCHANGED(UIText.CommitDialog_StatusAssumeUnchaged),
		UNKNOWN(UIText.CommitDialog_StatusUnknown);

		public String getText() {
			return myText;
		}

		private final String myText;

		private Status(String text) {
			myText = text;
		}
	}

