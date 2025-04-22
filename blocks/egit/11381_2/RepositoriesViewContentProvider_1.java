					if (subRepo != null) {
						final Repository cachedRepo = repositoryCache
								.lookupRepository(subRepo.getDirectory());
						subRepo.close();
						children.add(new RepositoryNode(node, cachedRepo));
					}
