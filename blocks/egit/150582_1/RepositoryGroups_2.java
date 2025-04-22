	public void setHidden(Collection<RepositoryGroup> groups,
			boolean hidden) {
		for (RepositoryGroup group : groups) {
			groupMap.get(group.getUuid()).setHidden(hidden);
		}
		savePreferences();
	}

