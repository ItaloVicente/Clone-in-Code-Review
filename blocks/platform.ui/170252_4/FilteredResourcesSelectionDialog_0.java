	private boolean isResourceType(IResource resource, int type) {
		if (resource == null) {
			return false;
		}
		return resource.getType() == type;
	}

