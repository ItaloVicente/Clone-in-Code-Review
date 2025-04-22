		IPath destLocation;
		URI containerLocation = mappedContainer.getLocationURI();
		if (containerLocation == null) {
			IPath rootLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			destLocation = rootLocation.append(mappedContainer.getName());
		} else
			destLocation = URIUtil.toPath(containerLocation);
		final IPath cLoc = destLocation
