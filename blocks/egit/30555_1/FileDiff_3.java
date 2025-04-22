
	private static class MoreAvailable extends FileDiff {
		private final String message;

		public MoreAvailable(final RevCommit c, final int numberOfShownEntries) {
			super(c, DiffEntry.NULL);
			message = NLS.bind(UIText.CommitFileDiffViewer_MoreFilesAvailable,
					numberOfShownEntries);
		}

		@Override
		public String getPath() {
			return message;
		}

		@Override
		public String getNewPath() {
			return message;
		}

		@Override
		public ChangeType getChange() {
			return ChangeType.MODIFY;
		}

		@Override
		public ObjectId[] getBlobs() {
			return new ObjectId[0];
		}

		@Override
		public FileMode[] getModes() {
			return new FileMode[0];
		}

		@Override
		public boolean isMarked(int index) {
			return false;
		}
	}
