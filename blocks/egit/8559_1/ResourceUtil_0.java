	public static ResourceMapping[] getResourceMappings(IFile file,
			ResourceMappingContext context) {
		final IModelProviderDescriptor[] modelDescriptors = ModelProvider
				.getModelProviderDescriptors();

		final Set<ResourceMapping> mappings = new LinkedHashSet<ResourceMapping>();
		for (IModelProviderDescriptor candidate : modelDescriptors) {
			try {
				final IResource[] resources = candidate
						.getMatchingResources(new IResource[] { file, });
				if (resources.length > 0) {
					final ModelProvider model = candidate.getModelProvider();

					final ResourceMapping[] modelMappings = model.getMappings(
							file, context, null);
					for (ResourceMapping mapping : modelMappings) {
						mappings.add(mapping);
					}
				}
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return mappings.toArray(new ResourceMapping[mappings.size()]);
	}

