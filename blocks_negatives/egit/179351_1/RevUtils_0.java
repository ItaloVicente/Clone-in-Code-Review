
	/**
	 * The interesting commits from ours/theirs for a file in case of a
	 * conflict.
	 */
	public static class ConflictCommits {
		private final RevCommit ourCommit;
		private final RevCommit theirCommit;

		private ConflictCommits(RevCommit ourCommit, RevCommit theirCommit) {
			this.ourCommit = ourCommit;
			this.theirCommit = theirCommit;
		}

		/**
		 * @return the commit from "ours" that last modified a file, or
		 *         {@code null} if none found
		 */
		public RevCommit getOurCommit() {
			return ourCommit;
		}

		/**
		 * @return the commit from "theirs" that last modified a file, or
		 *         {@code null} if none found
		 */
		public RevCommit getTheirCommit() {
			return theirCommit;
		}
	}
