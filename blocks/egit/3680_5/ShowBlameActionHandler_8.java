
	@Override
	public boolean isEnabled() {
		IResource[] selectedResources = getSelectedResources();
		return selectedResources.length == 1 &&
				!selectedResources[0].isLinked(IResource.CHECK_ANCESTORS);
	}
