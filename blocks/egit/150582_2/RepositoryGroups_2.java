
	public void setHideable(Collection<RepositoryGroup> groups,
			boolean hideable) {
		for (RepositoryGroup group : groups) {
			groupMap.get(group.getGroupId()).setHideable(hideable);
		}
		savePreferences();
	}

