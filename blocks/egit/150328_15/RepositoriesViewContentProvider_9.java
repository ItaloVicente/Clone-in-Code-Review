	private List<RepositoryNode> getRepositoryNodes(RepositoryUtil util,
			RepositoryGroups groupsUtil, RepositoryTreeNode<?> parent,
			List<File> directories) {
		List<RepositoryNode> result = new ArrayList<>();
		for (File gitDir : directories) {
			try {
				if (gitDir.exists()) {
					boolean addRepo = (groupsUtil == null
							|| !showRepositoryGroups
							|| !groupsUtil.belongsToGroup(gitDir));
					if (addRepo) {
						RepositoryNode rNode = new RepositoryNode(parent,
								repositoryCache.lookupRepository(gitDir));
						result.add(rNode);
					}
				} else {
					util.removeDir(gitDir);
				}
			} catch (IOException e) {
			}
		}
		return result;
	}

