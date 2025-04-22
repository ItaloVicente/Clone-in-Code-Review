					RepositoryCache cache = null;
					try {
						cache = RepositoryCache.getInstance();
					} finally {
						if (cache != null)
							cache.lookupRepository(subRepo.getDirectory())
									.close();
						subRepo.close();
					}
