	public static void openInCompare(RevCommit commit1, RevCommit commit2,
			String commit1Path, String commit2Path, Repository repository,
			IWorkbenchPage workBenchPage) {
		if (GitPreferenceRoot.useEclipseDiffTool()) {
			openInCompareInternal(commit1, commit2, commit1Path, commit2Path,
					repository, workBenchPage);
		} else {
			openInCompareExternal(commit1, commit2, commit1Path, commit2Path,
					repository, workBenchPage);
		}
	}

	private static void openInCompareInternal(RevCommit commit1,
