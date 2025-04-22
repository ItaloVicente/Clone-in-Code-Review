
		if (mapping.getRepoRelativePath(resource) == null) {
			return null;
		}

		IndexDiffCacheEntry diffCacheEntry = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache().getIndexDiffCacheEntry(repo);
