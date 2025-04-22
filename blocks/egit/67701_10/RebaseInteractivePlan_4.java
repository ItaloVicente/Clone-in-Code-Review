		Repository repository = getRepository();
		if (repository != null) {
			IndexDiffCacheEntry entry = org.eclipse.egit.core.Activator
					.getDefault().getIndexDiffCache()
					.getIndexDiffCacheEntry(repository);
			if (entry != null) {
				entry.removeIndexDiffChangedListener(this);
			}
