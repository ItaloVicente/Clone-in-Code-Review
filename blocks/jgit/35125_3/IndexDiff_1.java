	public void setIgnoreSubmoduleMode(IgnoreSubmoduleMode mode) {
		this.ignoreSubmoduleMode = mode;
	}

	public interface WorkingTreeIteratorFactory {
		public WorkingTreeIterator getWorkingTreeIterator(Repository repo);
	}

	private WorkingTreeIteratorFactory wTreeIt = new WorkingTreeIteratorFactory() {
		public WorkingTreeIterator getWorkingTreeIterator(Repository repo) {
			return new FileTreeIterator(repo);
		}
	};

	public void setWorkingTreeItFactory(WorkingTreeIteratorFactory wTreeIt) {
		this.wTreeIt = wTreeIt;
	}

