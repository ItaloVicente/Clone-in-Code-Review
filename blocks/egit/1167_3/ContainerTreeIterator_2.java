	@Override
	public boolean isEntryIgnored() throws IOException {
		return super.isEntryIgnored() ||
			isEntryIgnoredByTeamProvider(getResourceEntry().getResource());
	}

	private boolean isEntryIgnoredByTeamProvider(IResource resource) {
		if (resource instanceof IWorkspaceRoot)
			return false;
		if (Team.isIgnoredHint(resource))
			return true;
		return isEntryIgnoredByTeamProvider(resource.getParent());

	}

