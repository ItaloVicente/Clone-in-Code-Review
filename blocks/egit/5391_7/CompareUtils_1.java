		compareBetween(repository, gitPath, gitPath, leftRev, rightRev, page);
	}

	private static void compareBetween(Repository repository,
			String leftGitPath, String rightGitPath, String leftRev,
			String rightRev, IWorkbenchPage page) throws IOException {
		final ITypedElement left = getTypedElementFor(repository, leftGitPath,
