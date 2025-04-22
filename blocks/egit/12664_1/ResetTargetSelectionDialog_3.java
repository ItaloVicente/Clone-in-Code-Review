
	@Override
	public String getRefName() {
		String selected = super.getRefName();
		if (selected != null)
			return selected;
		return parsedCommitish;
	}
