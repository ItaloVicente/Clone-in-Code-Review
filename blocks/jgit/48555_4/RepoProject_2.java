	public List<CopyFile> getCopyFiles() {
		return Collections.unmodifiableList(copyfiles);
	}

	public String getUrl() {
		return url;
	}

	public String getRemote() {
		return remote;
	}

	public boolean inGroup(String group) {
		return groups.contains(group);
	}

