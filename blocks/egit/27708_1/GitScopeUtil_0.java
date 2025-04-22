		Set<ResourceMapping> result = new LinkedHashSet<ResourceMapping>();
		for (IResource resource : resources) {
			ResourceMapping[] additional = ResourceUtil.getResourceMappings(
					resource, ResourceMappingContext.LOCAL_CONTEXT);
			result.addAll(Arrays.asList(additional));
		}
