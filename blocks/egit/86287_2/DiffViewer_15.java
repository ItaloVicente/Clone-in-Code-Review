	private static ITypedElement createTypedElement(Repository repository,
			String path, final RevCommit commit, final ObjectId objectId) {
		if (null != commit)
			return CompareUtils.getFileRevisionTypedElement(path, commit,
					repository, objectId);
		else
			return new GitCompareFileRevisionEditorInput.EmptyTypedElement(""); //$NON-NLS-1$
