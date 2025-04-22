	private static class ExcludeBitmapRevFilter extends RevFilter {
		private final BitmapBuilder exclude;

		ExcludeBitmapRevFilter(BitmapBuilder exclude) {
			this.exclude = exclude;
		}

		@Override
		public final boolean include(RevWalk rw
			if (!exclude.contains(c)) {
				return true;
			}
			for (RevCommit p : c.getParents()) {
				p.add(SEEN);
			}
			return false;
		}

		@Override
		public final ExcludeBitmapRevFilter clone() {
			return this;
		}

		@Override
		public final boolean requiresCommitBody() {
			return false;
		}
	}

