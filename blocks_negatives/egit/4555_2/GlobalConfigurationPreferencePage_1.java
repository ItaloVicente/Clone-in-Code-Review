				try {
					Repository repository = repositoryCache.lookupRepository(new File(repoPath));
					repositories.add(repository);
				} catch (IOException e) {
					continue;
				}
