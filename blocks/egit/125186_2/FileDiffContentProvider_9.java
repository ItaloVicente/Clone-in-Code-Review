		cancel();
		viewer = null;
		diff = null;
		currentInput = null;
	}

	private static class FileDiffLoader extends Job {

		private FileDiff[] diffs;

		private final FileDiffInput input;

		private final TreeFilter filter;

		public FileDiffLoader(FileDiffInput input, TreeFilter filter) {
			super(MessageFormat.format(
					UIText.FileDiffContentProvider_computingFileDiffs,
					input.getCommit().getName()));
			this.input = input;
			this.filter = filter;
			setRule(new TreeWalkSchedulingRule(input.getTreeWalk()));
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				diffs = FileDiff.compute(input.getRepository(),
						input.getTreeWalk(),
						input.getCommit(), monitor, filter);
			} catch (IOException err) {
				Activator.handleError(MessageFormat.format(
						UIText.FileDiffContentProvider_errorGettingDifference,
						input.getCommit().getId()), err, false);
			}
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			return Status.OK_STATUS;
		}

		public FileDiff[] getDiffs() {
			return diffs;
		}
	}

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

