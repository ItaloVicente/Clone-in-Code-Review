		return isParentEntryIgnoredByTeamProvider(resource.getParent());
	}

	private boolean isParentEntryIgnoredByTeamProvider(IResource resource) {
		if (resource.getType() == IResource.ROOT
				|| resource.getType() == IResource.PROJECT)
			return false;
