	public static ITypedElement getFileRevisionTypedElementForCommonAncestor(
			final String gitPath, ObjectId commit1, ObjectId commit2,
			Repository db) {
		ITypedElement ancestor = null;
		RevCommit commonAncestor = null;
		try {
			commonAncestor = RevUtils.getCommonAncestor(db, commit1, commit2);
		} catch (IOException e) {
			Activator.logError(NLS.bind(UIText.CompareUtils_errorCommonAncestor,
					commit1.getName(), commit2.getName()), e);
		}
		if (commonAncestor != null)
			ancestor = CompareUtils
				.getFileRevisionTypedElement(gitPath, commonAncestor, db);
		return ancestor;
	}
