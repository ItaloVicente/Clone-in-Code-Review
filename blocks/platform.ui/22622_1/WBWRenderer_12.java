				for (Object resource : removeResources(engine
						.getResourcesRegistry())) {
					if (resource instanceof Resource
							&& !((Resource) resource).isDisposed()) {
						unusedResources.add((Resource) resource);
					}
				}
