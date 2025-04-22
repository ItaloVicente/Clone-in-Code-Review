	protected boolean selectionContainsLinkedResources() {
		IResource[] selectedResources = getSelectedResources();
		for (IResource res: selectedResources)
			if (res.isLinked(IResource.CHECK_ANCESTORS))
				return true;
		return false;
	}

