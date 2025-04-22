		this(repository, storage, path, startCommit, shell, page, -1);
	}

	public BlameOperation(Repository repository, IStorage storage, String path,
			AnyObjectId startCommit, Shell shell, IWorkbenchPage page,
			int lineNumberToReveal) {
