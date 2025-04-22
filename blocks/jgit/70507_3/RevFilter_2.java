	public static final RevFilter ONLY_MERGES = new OnlyMergesFilter();

	private static final class OnlyMergesFilter extends RevFilter {

		@Override
		public boolean include(RevWalk walker
			return c.getParentCount() >= 2;
		}

		@Override
		public RevFilter clone() {
			return this;
		}

		@Override
		public boolean requiresCommitBody() {
			return false;
		}

		@Override
		public String toString() {
		}
	}

