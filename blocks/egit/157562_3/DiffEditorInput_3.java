	private String title;

	private String tooltip;

	private final @NonNull IRepositoryCommit tip;

	private final IRepositoryCommit base;

	public DiffEditorInput(@NonNull IRepositoryCommit commit) {
		this(commit, null, null, null);
	}
