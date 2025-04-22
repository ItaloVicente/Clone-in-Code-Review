	/**
	 * Reverts the worktree after an unsuccessful merge. We know that for all
	 * modified files the old content was in the old index and the index
	 * contained only stage 0. In case if inCore operation just clear the
	 * history of modified files.
	 *
	 * @throws java.io.IOException
	 * @throws org.eclipse.jgit.errors.CorruptObjectException
	 * @throws org.eclipse.jgit.errors.NoWorkTreeException
	 * @since 3.4
	 */
	protected void cleanUp() throws NoWorkTreeException,
			CorruptObjectException,
			IOException {
		if (inCore) {
			modifiedFiles.clear();
			return;
		}

		DirCache dc = nonNullRepo().readDirCache();
		Iterator<String> mpathsIt=modifiedFiles.iterator();
		while(mpathsIt.hasNext()) {
			String mpath = mpathsIt.next();
			DirCacheEntry entry = dc.getEntry(mpath);
			if (entry != null) {
				DirCacheCheckout.checkoutEntry(db, entry, reader, false,
						cleanupMetadata.get(mpath));
			}
			mpathsIt.remove();
		}
	}
