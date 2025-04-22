	public String getBranch() throws IOException {
		String name = getFullBranch();
		if (name != null)
			return shortenRefName(name);
		return name;
	}
