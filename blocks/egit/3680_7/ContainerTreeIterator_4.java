	@Override
	public boolean isEntryIgnored() throws IOException {
		return super.isEntryIgnored() ||
			getResourceEntry().getResource().isLinked(IResource.CHECK_ANCESTORS);
	}

