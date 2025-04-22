		Set<IResource> newResources = new HashSet<IResource>(
				includedResources);
		do {
			final Set<IResource> copy = newResources;
			newResources = new HashSet<IResource>();
			for (IResource resource : copy) {
				if (resource instanceof IFile) {
					ResourceMapping[] mappings = ResourceUtil
							.getResourceMappings((IFile) resource,
									ResourceMappingContext.LOCAL_CONTEXT);
					allMappings.addAll(Arrays.asList(mappings));
					newResources.addAll(collectResources(mappings));
				}
			}
		} while (includedResources.addAll(newResources));
