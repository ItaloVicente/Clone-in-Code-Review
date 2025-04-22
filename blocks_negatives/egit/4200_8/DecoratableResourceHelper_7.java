				switch (resource.getType()) {
				case IResource.FILE:
					resourcePaths.add(mapping.getRepoRelativePath(resource));
					break;
				case IResource.FOLDER:
				case IResource.PROJECT:
					try {
						decoratableResources[i] = new DecoratableResourceAdapter(
								resource);
					} catch (IOException e) {
					}
					resourcePaths.add(null);
					break;
