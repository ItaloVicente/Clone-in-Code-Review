	private WorkingTreeIteratorFactory wTreeIt = new WorkingTreeIteratorFactory() {
		@Override
		public WorkingTreeIterator getWorkingTreeIterator(Repository repo) {
			return new FileTreeIterator(repo);
		}
	};
