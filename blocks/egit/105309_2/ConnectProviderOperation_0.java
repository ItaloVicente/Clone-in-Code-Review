			if (gitPath != null) {
				try {
					Repository repository = org.eclipse.egit.core.Activator
							.getDefault().getRepositoryCache()
							.lookupRepository(gitPath.toFile());
					IndexDiffCacheEntry cacheEntry = org.eclipse.egit.core.Activator
							.getDefault().getIndexDiffCache()
							.getIndexDiffCacheEntry(repository);
					if (cacheEntry != null) {
						cacheEntry.refresh();
					}
				} catch (IOException e) {
					Activator.logError(e.getMessage(), e);
				}
