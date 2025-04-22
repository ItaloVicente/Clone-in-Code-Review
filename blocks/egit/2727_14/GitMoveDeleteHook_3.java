		IPath newLocation = null;
		if (description.getLocationURI() != null)
			newLocation = URIUtil.toPath(description.getLocationURI());
		else
			newLocation = source.getWorkspace().getRoot().getLocation()
					.append(description.getName());
		IPath sourceLocation = source.getLocation();
		File newLocationFile = newLocation.toFile();
