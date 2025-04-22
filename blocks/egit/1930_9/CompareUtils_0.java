
	public static ITypedElement getFileCachedRevisionTypedElement(String gitPath,
			Repository db) {
		try {
			DirCache dc = db.lockDirCache();
			DirCacheEntry entry = dc.getEntry(gitPath);
			dc.unlock();

			if (entry != null) {
				return new FileRevisionTypedElement(GitFileRevision.inIndex(db, gitPath));
			}
		} catch (IOException e) {
			Activator.error(NLS.bind(UIText.GitHistoryPage_errorLookingUpPath,
					gitPath), e);
		}

		return new GitCompareFileRevisionEditorInput.EmptyTypedElement(NLS
				.bind(UIText.CompareWithIndexAction_FileNotInIndex,
						gitPath.substring(gitPath.lastIndexOf("/")) + 1)); //$NON-NLS-1$
	}

