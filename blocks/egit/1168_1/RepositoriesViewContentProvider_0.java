				File gitDir = new File(directory);
				if (gitDir.exists()) {
					RepositoryNode rNode = new RepositoryNode(null, repositoryCache
							.lookupRepository(gitDir));
					nodes.add(rNode);
				} else {
					repositoryUtil.removeDir(gitDir);
				}
