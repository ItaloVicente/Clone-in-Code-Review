				IResourceDelta delta = event.getDelta();
				IResource resource = null;
				if (delta != null) {
					resource = delta.getResource();
				}
				if (resource != null
						&& (resource.getType() == IResource.PROJECT || resource.getType() == IResource.ROOT)) {
