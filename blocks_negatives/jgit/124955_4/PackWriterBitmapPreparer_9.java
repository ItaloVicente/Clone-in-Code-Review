	/**
	 * A POJO representing a Pair<RevCommit, BitmapBuidler>.
	 */
	private static final class BitmapBuilderEntry {
		private final RevCommit commit;
		private final BitmapBuilder builder;

		BitmapBuilderEntry(RevCommit commit, BitmapBuilder builder) {
			this.commit = commit;
			this.builder = builder;
		}

		RevCommit getCommit() {
			return commit;
		}

		BitmapBuilder getBuilder() {
			return builder;
		}
	}

