	private static class ReflogSchedulingRule implements ISchedulingRule {

		private final File gitDir;

		public ReflogSchedulingRule(File gitDir) {
			this.gitDir = gitDir;
		}

		@Override
		public boolean contains(ISchedulingRule rule) {
			if (rule instanceof ReflogSchedulingRule) {
				return Objects.equals(gitDir,
						((ReflogSchedulingRule) rule).gitDir);
			}
			return false;
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return rule instanceof ReflogSchedulingRule;
		}

	}

