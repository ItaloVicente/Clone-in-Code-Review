		resource.accept(new IResourceProxyVisitor() {
			@Override
			public boolean visit(IResourceProxy proxy) {
				IResource childResource = proxy.requestResource();
				URI uri = childResource.getLocationURI();
				if (!visited.contains(uri)) {
					visited.add(uri);
					toVisit.add(childResource);
				}
				return true;
