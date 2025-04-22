public class FetchResultTable {

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
