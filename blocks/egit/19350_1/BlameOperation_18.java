	private int lineNumberToReveal;

	public BlameOperation(Repository repository, IStorage storage, String path,
			RevCommit startCommit, Shell shell, IWorkbenchPage page) {
		this(repository, storage, path, startCommit, shell, page, -1);
	}

