					if (!useRepositoryGroups
							|| !groups.belongsToGroup(directory)) {
						RepositoryNode rNode = new RepositoryNode(null,
								repositoryCache.lookupRepository(gitDir));
						nodes.add(rNode);
					}
