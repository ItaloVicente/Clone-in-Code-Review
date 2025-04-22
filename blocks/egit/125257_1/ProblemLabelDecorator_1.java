			IPath path = AdapterUtils.adapt(element, IPath.class);
			if (path == null) {
				IResource resource = AdapterUtils.adapt(element,
						IResource.class);
				if (resource != null) {
					path = resource.getLocation();
				}
			}
			if (path != null && paths.contains(path)) {
