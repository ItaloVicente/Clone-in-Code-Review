	public void expandNodeForGroup(RepositoryGroup group) {
		if (group != null) {
			getCommonViewer().expandToLevel(new RepositoryGroupNode(group), 1);
		}
	}

