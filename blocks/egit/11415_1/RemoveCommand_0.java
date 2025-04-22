					RepositoryCache cache = null;
					try {
						cache = org.eclipse.egit.core.Activator.getDefault()
								.getRepositoryCache();
					} finally {
						if (cache != null)
							cache.lookupRepository(subRepo.getDirectory())
									.close();
						subRepo.close();
					}
