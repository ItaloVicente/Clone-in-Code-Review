			IPath location = resource.getLocation();
			if (location == null) {
				return false;
			}
			repositoryOfResource = ResourceUtil.getRepository(location);
			if (repository != repositoryOfResource) {
				return false;
			}
		} else {
			repositoryOfResource = ResourceUtil.getRepository(resource);
