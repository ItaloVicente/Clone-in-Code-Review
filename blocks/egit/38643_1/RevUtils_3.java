	public static class ConflictCommits {
		private final RevCommit ourCommit;
		private final RevCommit theirCommit;

		private ConflictCommits(RevCommit ourCommit, RevCommit theirCommit) {
			this.ourCommit = ourCommit;
			this.theirCommit = theirCommit;
		}

		public RevCommit getOurCommit() {
			return ourCommit;
		}

		public RevCommit getTheirCommit() {
			return theirCommit;
		}
	}
