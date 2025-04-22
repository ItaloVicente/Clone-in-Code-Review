		if (resource.isLinked(IResource.CHECK_ANCESTORS)) {
			IPath location = resource.getLocation();
			if (location == null) {
				return null;
			}
			return getMapping(location);
