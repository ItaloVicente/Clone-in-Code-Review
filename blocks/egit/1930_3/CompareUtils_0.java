
	public static ITypedElement getFileCachedRevisionTypedElement(final String gitPath,
			final Repository db) {
		try {
			ObjectId headId = db.getRef(HEAD).getObjectId();

			TreeWalk w = new TreeWalk(db);
			w.setRecursive(true);
			w.addTree(new DirCacheIterator(db.readDirCache()));
			w.addTree(new RevWalk(db).parseTree(headId));

			w.setFilter(AndTreeFilter.create(ANY_DIFF, PathFilter.create(gitPath)));

			if (w.next() && isCachedEntry(w, 1, 2)) {
				return new FileRevisionTypedElement(GitFileRevision.inIndex(db, gitPath));
			}
		} catch (IOException e) {
			Activator.error(NLS.bind(UIText.GitHistoryPage_errorLookingUpPath,
					gitPath), e);
		}

		return null;
	}

	public static boolean isCachedEntry(TreeWalk tw, int baseNth, int remoteNth) {
		final int mHead = tw.getRawMode(baseNth);
		final int mCache = tw.getRawMode(remoteNth);

		return mHead == MISSING.getBits() // initial add to cache
				|| mCache == MISSING.getBits() // removed from cache
				|| (mHead != mCache || (mCache != TREE.getBits() && !tw
						.idEqual(baseNth, remoteNth))); // modified
	}

