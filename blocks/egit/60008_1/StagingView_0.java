			Repository repository = currentRepository;
			if (repository != null) {
				IndexDiffCacheEntry indexDiffCacheForRepository = org.eclipse.egit.core.Activator
						.getDefault().getIndexDiffCache()
						.getIndexDiffCacheEntry(repository);
				if (indexDiffCacheForRepository != null) {
					indexDiffCacheForRepository
							.refreshFiles(Arrays.asList(files));
				}
			}
