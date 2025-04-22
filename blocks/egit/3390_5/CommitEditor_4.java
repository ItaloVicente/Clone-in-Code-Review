	public static final IEditorPart openQuiet(RepositoryCommit commit) {
		try {
			return open(commit);
		} catch (PartInitException e) {
			Activator.logError(e.getMessage(), e);
			return null;
		}
	}

	private CommitEditorPage commitPage;

	private DiffEditorPage diffPage;

	private ListenerHandle refListenerHandle;

