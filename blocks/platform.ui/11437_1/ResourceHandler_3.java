	private Resource getResource(URI uri) throws Exception {
		Resource resource;
		if (saveAndRestore) {
			resource = resourceSetImpl.getResource(uri, true);
		} else {
			resource = resourceSetImpl.createResource(uri);
			resource.load(new URL(uri.toString()).openStream(), resourceSetImpl.getLoadOptions());
		}

		return resource;
	}

