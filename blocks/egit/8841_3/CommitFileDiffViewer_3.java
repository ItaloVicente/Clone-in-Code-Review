	private ITypedElement createTypedElement(final String path,
			final RevCommit commit, final ObjectId objectId) {
		if (null != commit)
			return CompareUtils.getFileRevisionTypedElement(path, commit,
					getRepository(), objectId);
		else
			return new GitCompareFileRevisionEditorInput.EmptyTypedElement(""); //$NON-NLS-1$
