	private void refreshIndexDelta() {
		if (repository.isBare())
			return;

		try {
			DirCache currentIndex = repository.readDirCache();
			DirCache oldIndex = lastIndex;

			lastIndex = currentIndex;

			if (oldIndex == null) {
				refresh(); // full refresh in case we have no data to compare.
				return;
			}

			Set<String> paths = new TreeSet<String>();
			TreeWalk walk = new TreeWalk(repository);

			try {
				walk.addTree(new DirCacheIterator(oldIndex));
				walk.addTree(new DirCacheIterator(currentIndex));
				walk.setFilter(TreeFilter.ANY_DIFF);

				while (walk.next()) {
					if(walk.isSubtree())
						walk.enterSubtree();
					else
						paths.add(walk.getPathString());
				}
			} finally {
				walk.release();
			}

			if (!paths.isEmpty())
				refreshFiles(paths);

		} catch (IOException ex) {
			Activator.error(String.format(CoreText.IndexDiffCacheEntry_errorCalculatingIndexDelta, repository), ex);
			scheduleReloadJob("Exception while calculating index delta, doing full reload instead"); //$NON-NLS-1$
		}
	}

