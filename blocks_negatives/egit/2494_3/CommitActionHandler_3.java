	private Map<Repository, IndexDiff> indexDiffs;

	private Set<IFile> notIndexed;

	private Set<IFile> indexChanges;

	private Set<IFile> notTracked;

	private Set<IFile> files;

	private RevCommit previousCommit;

	private boolean amendAllowed;

	private boolean amending;

