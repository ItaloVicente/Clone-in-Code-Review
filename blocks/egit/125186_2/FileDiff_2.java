	public static FileDiff[] compute(Repository repository, TreeWalk walk,
			RevCommit commit, RevCommit[] parents,
			@Nullable IProgressMonitor monitor, TreeFilter... markTreeFilters)
			throws MissingObjectException, IncorrectObjectTypeException,
			CorruptObjectException, IOException {

