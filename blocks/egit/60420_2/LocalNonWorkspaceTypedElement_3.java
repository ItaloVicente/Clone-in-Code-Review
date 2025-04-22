					} else {
						Repository myRepository = repository;
						if (myRepository != null) {
							IPath repositoryRoot = new Path(
									myRepository.getWorkTree().getPath());
							IPath relativePath = path
									.makeRelativeTo(repositoryRoot);
							IndexDiffCacheEntry indexDiffCacheForRepository = org.eclipse.egit.core.Activator
									.getDefault().getIndexDiffCache()
									.getIndexDiffCacheEntry(myRepository);
							if (indexDiffCacheForRepository != null) {
								indexDiffCacheForRepository
										.refreshFiles(Collections.singleton(
												relativePath.toString()));
							}
						}
					}
