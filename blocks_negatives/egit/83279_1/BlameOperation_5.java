	public BlameOperation(Repository repository, IStorage storage, String path,
			RevCommit startCommit, Shell shell, IWorkbenchPage page,
			int lineNumberToReveal) {
		this.repository = repository;
		this.storage = storage;
		this.path = path;
		this.startCommit = startCommit;
