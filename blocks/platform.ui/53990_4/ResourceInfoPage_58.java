		resource.accept(proxy -> {
			IResource childResource = proxy.requestResource();
			URI uri = childResource.getLocationURI();
			if (!visited.contains(uri)) {
				visited.add(uri);
				toVisit.add(childResource);
