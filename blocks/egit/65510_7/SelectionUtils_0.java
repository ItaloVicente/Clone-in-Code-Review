	@NonNull
	private static Set<Object> getSelectionContents(
			@NonNull IStructuredSelection selection) {
		Set<Object> result = new HashSet<>();
		for (Object o : selection.toList()) {
			IResource resource = AdapterUtils.adapt(o, IResource.class);
			if (resource != null) {
				result.add(resource);
				continue;
			}
			ResourceMapping mapping = AdapterUtils.adapt(o,
					ResourceMapping.class);
			if (mapping != null) {
				result.addAll(extractResourcesFromMapping(mapping));
			} else {
				IPath location = AdapterUtils.adapt(o, IPath.class);
				if (location != null) {
					result.add(location);
				}
			}
		}
		return result;
	}

	@Nullable
	private static Repository getRepository(@NonNull IPath location) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(location);
		if (mapping != null) {
			return mapping.getRepository();
		}
		return org.eclipse.egit.core.Activator.getDefault().getRepositoryCache()
				.getRepository(location);
	}

	@Nullable
	private static Repository getRepository(@NonNull IResource resource) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		if (mapping != null) {
			return mapping.getRepository();
		}
		return org.eclipse.egit.core.Activator.getDefault().getRepositoryCache()
				.getRepository(resource);
	}

