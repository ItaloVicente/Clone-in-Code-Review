
	private class UpdateJob extends UIJob {

		private final FileDiffLoader loadJob;

		public UpdateJob(String name, FileDiffLoader loadJob) {
			super(name);
			this.loadJob = loadJob;
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			Control control = getControl();
			if (control == null || control.isDisposed() || loader != loadJob) {
				return Status.CANCEL_STATUS;
			}
			FileDiff[] diffs = loadJob.getDiffs();
			try {
				control.setRedraw(false);
				setInput(diffs);
				FileDiff interesting = getFirstInterestingElement(diffs);
				if (interesting != null) {
					if (loadJob.getInput().isSelectMarked()) {
						setSelection(new StructuredSelection(interesting),
								true);
					} else {
						reveal(interesting);
					}
				}
			} finally {
				control.setRedraw(true);
			}
			return Status.OK_STATUS;
		}

		private FileDiff getFirstInterestingElement(FileDiff[] diffs) {
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

