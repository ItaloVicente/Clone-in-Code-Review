	private static void compareBetween(Repository repository,
			String leftGitPath, String rightGitPath, String leftRev,
			String rightRev, IWorkbenchPage page) throws IOException {
		final ITypedElement left = getTypedElementFor(repository, leftGitPath,
				leftRev);
		final ITypedElement right = getTypedElementFor(repository,
				rightGitPath,
				rightRev);

		final ITypedElement commonAncestor;
		if (left != null && right != null && !GitFileRevision.INDEX.equals(leftRev)
				&& !GitFileRevision.INDEX.equals(rightRev))
			commonAncestor = getTypedElementForCommonAncestor(repository,
					rightGitPath, leftRev, rightRev);
		else
			commonAncestor = null;
