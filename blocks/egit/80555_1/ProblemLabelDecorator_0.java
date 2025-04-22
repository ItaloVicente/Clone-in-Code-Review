		for (IMarkerDelta delta : markerDeltas) {
			IResource resource = delta.getResource();
			while (resource.getType() != IResource.ROOT
					&& resources.add(resource)) {
				resource = resource.getParent();
			}
		}
