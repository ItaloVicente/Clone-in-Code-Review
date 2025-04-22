	public static void openInCompare(RevCommit commit1,
			RevCommit commit2, String commit1Path, String commit2Path,
			Repository repository, IWorkbenchPage workbenchPage) {
		final ITypedElement base = CompareUtils
				.getFileRevisionTypedElement(commit1Path, commit1, repository);
		final ITypedElement next = CompareUtils
				.getFileRevisionTypedElement(commit2Path, commit2, repository);
