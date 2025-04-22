
	private static class FileDiffForMerges extends FileDiff {
		private String path;

		private ChangeType change;

		private ObjectId[] blobs;

		private FileMode[] modes;

		private FileDiffForMerges(final RevCommit c) {
			super (c, null);
		}

		@Override
		public String getPath() {
			return path;
		}

		@Override
		public ChangeType getChange() {
			return change;
		}

		@Override
		public ObjectId[] getBlobs() {
			return blobs;
		}

		@Override
		public FileMode[] getModes() {
			return modes;
		}
	}
