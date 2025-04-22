
	private static class TreeWalkSchedulingRule implements ISchedulingRule {

		private final TreeWalk treeWalk;

		public TreeWalkSchedulingRule(TreeWalk treeWalk) {
			this.treeWalk = treeWalk;
		}

		@Override
		public boolean contains(ISchedulingRule rule) {
			if (rule instanceof TreeWalkSchedulingRule) {
				return Objects.equals(treeWalk,
						((TreeWalkSchedulingRule) rule).treeWalk);
			}
			return false;
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return contains(rule);
		}

	}

