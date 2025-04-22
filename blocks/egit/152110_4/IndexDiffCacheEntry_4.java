		try {
			UnitOfWork.run(repository, () -> {
				try (SubmoduleWalk walk = SubmoduleWalk.forIndex(repository)) {
					while (walk.next()) {
						Repository submodule = walk.getRepository();
						if (submodule != null && !submodule.isBare()) {
							Repository cached = org.eclipse.egit.core.Activator
									.getDefault().getRepositoryCache()
									.lookupRepository(submodule.getDirectory()
											.getAbsoluteFile());
							submodules.put(cached, walk.getPath());
							IndexDiffCacheEntry submoduleCache = org.eclipse.egit.core.Activator
									.getDefault().getIndexDiffCache()
									.getIndexDiffCacheEntry(cached);
							if (submoduleCache != null) {
								submoduleCache.addIndexDiffChangedListener(
										submoduleListener);
							}
							submodule.close();
						}
