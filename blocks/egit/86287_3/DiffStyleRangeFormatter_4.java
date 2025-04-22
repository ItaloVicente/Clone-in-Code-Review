	public static class FileDiffRange {
		private final int startOffset;

		private final int endOffset;

		private final FileDiff diff;

		private final Repository repository;

		public FileDiffRange(Repository repository, FileDiff fileDiff,
				int start, int end) {
			this.startOffset = start;
			this.endOffset = end;
			this.diff = fileDiff;
			this.repository = repository;
		}

		public int getStartOffset() {
			return startOffset;
		}

		public int getEndOffset() {
			return endOffset;
		}

		public FileDiff getDiff() {
			return diff;
		}

		public Repository getRepository() {
			return repository;
		}

	}

