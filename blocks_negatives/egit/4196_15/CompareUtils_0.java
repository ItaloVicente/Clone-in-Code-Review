	/**
	 * Creates {@link ITypedElement} of file that was cached
	 *
	 * @param gitPath
	 * @param db
	 * @return {@link ITypedElement} instance for given cached file or
	 *         {@code null} if file isn't cached
	 */
	public static ITypedElement getFileCachedRevisionTypedElement(String gitPath,
			Repository db) {
		try {
			DirCache dc = db.lockDirCache();
			DirCacheEntry entry = dc.getEntry(gitPath);
			dc.unlock();

			if (entry != null) {
				GitFileRevision nextFile = GitFileRevision.inIndex(db, gitPath);
				String encoding = CompareCoreUtils.getResourceEncoding(db, gitPath);
				return new FileRevisionTypedElement(nextFile, encoding);
			}
		} catch (IOException e) {
			Activator.error(NLS.bind(UIText.GitHistoryPage_errorLookingUpPath,
					gitPath), e);
		}

		return new GitCompareFileRevisionEditorInput.EmptyTypedElement(NLS
				.bind(UIText.CompareWithIndexAction_FileNotInIndex,
	}

