
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

	private class UpdateJob extends UIJob {

		FileDiffLoader loadJob;

		public UpdateJob(String name, FileDiffLoader loadJob) {
			super(name);
			this.loadJob = loadJob;
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			if (viewer.getControl().isDisposed() || loader != loadJob) {
				return Status.CANCEL_STATUS;
			}
			diff = loadJob.getDiffs();
			viewer.refresh();
			FileDiff interesting = getFirstInterestingElement();
			if (interesting != null) {
				if (currentInput.isSelectMarked()) {
					viewer.setSelection(new StructuredSelection(interesting),
							true);
				} else {
					viewer.reveal(interesting);
				}
			}
			return Status.OK_STATUS;
		}

		private FileDiff getFirstInterestingElement() {
			FileDiff[] diffs = diff;
			if (diffs != null) {
				for (FileDiff d : diffs) {
					if (d.isMarked(INTERESTING_MARK_TREE_FILTER_INDEX)) {
						return d;
					}
				}
			}
			return null;
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

	}

