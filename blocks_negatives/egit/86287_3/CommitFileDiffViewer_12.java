
		final ITypedElement oldSide = createTypedElement(op, oldCommit,
				oldObjectId);
		final ITypedElement newSide = createTypedElement(np, newCommit,
				newObjectId);
		CompareUtils.openInCompare(page, new GitCompareFileRevisionEditorInput(
				newSide, oldSide, null));
	}

	private ITypedElement createTypedElement(final String path,
			final RevCommit commit, final ObjectId objectId) {
		if (null != commit)
			return CompareUtils.getFileRevisionTypedElement(path, commit,
					getRepository(), objectId);
		else
