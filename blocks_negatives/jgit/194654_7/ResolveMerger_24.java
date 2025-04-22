	 * @return whether the trees merged cleanly
	 * @throws java.io.IOException
	 * @since 3.5
	 */
	protected boolean mergeTrees(AbstractTreeIterator baseTree,
			RevTree headTree, RevTree mergeTree, boolean ignoreConflicts)
			throws IOException {

		builder = dircache.builder();
		DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder);

		tw = new NameConflictTreeWalk(db, reader);
		tw.addTree(baseTree);
		tw.setHead(tw.addTree(headTree));
		tw.addTree(mergeTree);
		int dciPos = tw.addTree(buildIt);
		if (workingTreeIterator != null) {
			tw.addTree(workingTreeIterator);
			workingTreeIterator.setDirCacheIterator(tw, dciPos);
		} else {
			tw.setFilter(TreeFilter.ANY_DIFF);
		}

		if (!mergeTreeWalk(tw, ignoreConflicts)) {
			return false;
		}

		if (!inCore) {
			checkout();

			if (!builder.commit()) {
				cleanUp();
				throw new IndexWriteException();
			}
			builder = null;

		} else {
			builder.finish();
			builder = null;
		}

		if (getUnmergedPaths().isEmpty() && !failed()) {
			resultTree = dircache.writeTree(getObjectInserter());
			return true;
		}
		resultTree = null;
		return false;
	}

	/**
	 * Process the given TreeWalk's entries.
	 *
