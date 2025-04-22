	private List<RepositoryNode> getRepositoryNodes(RepositoryUtil util,
			RepositoryGroups groups, RepositoryTreeNode<?> parent,
			List<String> directories) {
		List<RepositoryNode> result = new ArrayList<>();
		for (String directory : directories) {
			try {
				File gitDir = new File(directory);
				if (gitDir.exists()) {
					boolean addRepo = (groups == null || !showRepositoryGroups
							|| !groups.belongsToGroup(directory));
					if (addRepo) {
						RepositoryNode rNode = new RepositoryNode(parent,
								repositoryCache.lookupRepository(gitDir));
						result.add(rNode);
					}
				} else
					util.removeDir(gitDir);
			} catch (IOException e) {
			}
		}
		return result;
	}

