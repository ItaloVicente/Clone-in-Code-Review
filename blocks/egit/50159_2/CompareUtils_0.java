		if (GitPreferenceRoot.useEclipseDiffTool()) {
			openInCompareInternal(commit1, commit2, commit1Path, commit2Path,
					repository, workBenchPage);
		} else {
			openInCompareExternal(commit1, commit2, commit1Path, commit2Path,
					repository, workBenchPage);
		}
	}

	private static void openInCompareInternal(RevCommit commit1,
			RevCommit commit2, String commit1Path, String commit2Path,
			Repository repository, IWorkbenchPage workBenchPage) {
		final ITypedElement base = CompareUtils
				.getFileRevisionTypedElement(commit1Path, commit1, repository);
		final ITypedElement next = CompareUtils
				.getFileRevisionTypedElement(commit2Path, commit2, repository);
