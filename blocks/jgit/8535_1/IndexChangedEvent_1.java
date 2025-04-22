	public synchronized List<String> getModifiedPaths() {
		if(changes != null)
			return changes;

		Repository repo = getRepository();
		changes = new ArrayList<String>();
		try {
			synchronized (repo) {
				DirCache oldCache = oldCaches.get(repo);
				DirCache newCache = DirCache.read(repo);

				if(newCache.equals(oldCache))
					return changes;

				TreeWalk tw = new TreeWalk(repo);
				try {
					tw.addTree(new DirCacheIterator(oldCache));
					tw.addTree(new DirCacheIterator(newCache));
					tw.setFilter(TreeFilter.ANY_DIFF);

					while (tw.next()) {
						if (tw.isSubtree())
							tw.enterSubtree();
						else
							changes.add(tw.getPathString());
					}
				} finally {
					tw.release();
				}

				oldCaches.put(repo
			}
		} catch (Exception e) {
			throw new JGitInternalException("Error calculating changed files"
					e);
		}

		return changes;
	}

	public static void setInitialIndex(Repository repository) {
		synchronized (repository) {
			try {
				oldCaches.put(repository
			} catch (Exception e) {
			}
		}
	}

