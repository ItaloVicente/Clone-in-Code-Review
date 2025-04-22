		if (implicitDirCache) {
			dircache = nonNullRepo().lockDirCache();
		}
		if (!inCore) {
			checkoutMetadata = new HashMap<>();
			cleanupMetadata = new HashMap<>();
		}
		try {
			return mergeTrees(mergeBase(), sourceTrees[0], sourceTrees[1],
					false);
		} finally {
			checkoutMetadata = null;
			cleanupMetadata = null;
			if (implicitDirCache) {
				dircache.unlock();
			}
		}
	}

	private void checkout() throws NoWorkTreeException, IOException {
		for (int i = toBeDeleted.size() - 1; i >= 0; i--) {
			String fileName = toBeDeleted.get(i);
			File f = new File(nonNullRepo().getWorkTree(), fileName);
			if (!f.delete())
				if (!f.isDirectory())
					failingPaths.put(fileName,
							MergeFailureReason.COULD_NOT_DELETE);
			modifiedFiles.add(fileName);
		}
		for (Map.Entry<String, DirCacheEntry> entry : toBeCheckedOut
				.entrySet()) {
			DirCacheEntry cacheEntry = entry.getValue();
			if (cacheEntry.getFileMode() == FileMode.GITLINK) {
				new File(nonNullRepo().getWorkTree(), entry.getKey()).mkdirs();
			} else {
				DirCacheCheckout.checkoutEntry(db, cacheEntry, reader, false,
						checkoutMetadata.get(entry.getKey()));
				modifiedFiles.add(entry.getKey());
			}
		}
	}

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
