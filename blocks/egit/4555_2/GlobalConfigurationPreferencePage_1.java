				File gitDir = new File(repoPath);
				if (gitDir.exists())
					try {
						repositories.add(repositoryCache
								.lookupRepository(gitDir));
					} catch (IOException e) {
						continue;
					}
