
	@Override
	public boolean isEnabled() {
		IResource[] selectedResources = getSelectedResources();
		return selectedResources.length == 1 &&
				selectionMapsToSingleRepository();
	}
