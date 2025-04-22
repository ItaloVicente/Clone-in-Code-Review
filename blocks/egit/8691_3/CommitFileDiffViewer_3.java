	private ITypedElement createTypedElement(final String path,
			final RevCommit commit, final ObjectId objectId) {
		final ITypedElement base;
		if (null != commit)
			base = CompareUtils.getFileRevisionTypedElement(path, commit,
					getRepository(), objectId);
		else
			base = new GitCompareFileRevisionEditorInput.EmptyTypedElement(""); //$NON-NLS-1$
		return base;
