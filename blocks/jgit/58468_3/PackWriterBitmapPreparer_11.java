	private static final class BitmapBuilderEntry {
		private final RevCommit commit;

		private final BitmapBuilder builder;

		BitmapBuilderEntry(RevCommit commit
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

	private static final class CommitSelectionHelper implements Iterable<RevCommit> {
		final Set<? extends ObjectId> peeledWants;

		final List<BitmapBuilderEntry> tipCommitBitmaps;
		final Iterable<BitmapCommit> reusedCommits;
