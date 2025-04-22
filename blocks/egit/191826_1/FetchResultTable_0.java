	private static class CommitAdapter extends RepositoryCommit
			implements IDeferredWorkbenchAdapter, ISchedulingRule {

		private volatile FileDiff[] diffs;

		CommitAdapter(Repository repository, RevCommit commit) {
			super(repository, commit);
		}

		@Override
		public Object[] getChildren(Object o) {
			return diffs;
		}

		@Override
		public void fetchDeferredChildren(Object object,
				IElementCollector collector, IProgressMonitor monitor) {
			if (diffs != null) {
				return;
			}
			diffs = getDiffs();
			collector.add(diffs, monitor);
		}

		@Override
		public boolean isContainer() {
			return true;
		}

		@Override
		public ISchedulingRule getRule(Object object) {
			return this;
		}

		@Override
		public boolean contains(ISchedulingRule rule) {
			return this == rule;
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return this == rule;
		}
	}

	private class FetchResultAdapter extends WorkbenchAdapter
			implements IDeferredWorkbenchAdapter, ISchedulingRule {
