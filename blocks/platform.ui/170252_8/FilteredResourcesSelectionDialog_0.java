	private boolean parentIsRoot(IResource resource) {
		if (resource.getParent() == null) {
			return false;
		}
		return resource.getParent().getType() == IResource.ROOT;
	}

