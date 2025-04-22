		private static class PreviousCommit {

			final RevCommit commit;

			final String path;

			PreviousCommit(final RevCommit commit, final String path) {
				this.commit = commit;
				this.path = path;
			}
		}

