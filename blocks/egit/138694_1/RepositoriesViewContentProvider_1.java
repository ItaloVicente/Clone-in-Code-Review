		if (useRepositoryGroups) {
			for (String group : groups.getGroupNames()) {
				nodes.add(new RepositoryGroupNode(null, group,
						!groups.getRepositories(group).isEmpty()));
			}
		}
