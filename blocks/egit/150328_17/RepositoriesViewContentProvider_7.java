		nodes.addAll(
				getRepositoryNodes(repositoryUtil, groupsUtil, null,
						directories));
		if (showRepositoryGroups) {
			for (RepositoryGroup group : groupsUtil.getGroups()) {
				nodes.add(new RepositoryGroupNode(group));
