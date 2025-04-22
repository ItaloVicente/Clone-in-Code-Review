			IndexDiffCacheEntry cacheEntry = org.eclipse.egit.core.Activator
					.getDefault().getIndexDiffCache()
					.getIndexDiffCacheEntry(actualMapping.getRepository());
			if (cacheEntry != null) {
				cacheEntry.refresh();
			}
