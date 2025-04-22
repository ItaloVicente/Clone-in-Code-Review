					Repository subRepo = walk.getRepository();
					if (subRepo != null) {
						Repository cachedRepo = null;
						try {
							cachedRepo = repositoryCache
								.lookupRepository(subRepo.getDirectory());
						} finally {
							subRepo.close();
						}
						if (cachedRepo != null)
							children.add(new RepositoryNode(node, cachedRepo));
