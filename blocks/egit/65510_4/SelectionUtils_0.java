	private static boolean addResource(@Nullable IResource resource,
			@NonNull Set<Object> result) {
		if (resource == null) {
			return false;
		}
		IPath location = resource.getLocation();
		if (location != null) {
			if (!resource.isLinked(IResource.CHECK_ANCESTORS)) {
				IProject project = resource.getProject();
				if (project != null) {
					result.add(project);
					return true;
				}
			}
			result.add(location);
			return true;
		}
		return false;
	}

	@NonNull
	private static Set<Object> getSelectionContents(
			@NonNull IStructuredSelection selection) {
		Set<Object> result = new HashSet<>();
		for (Object o : selection.toList()) {
			IResource resource = AdapterUtils.adapt(o, IResource.class);
			if (addResource(resource, result)) {
				continue;
			}
			ResourceMapping mapping = AdapterUtils.adapt(o,
					ResourceMapping.class);
			if (mapping != null) {
				for (IResource r : extractResourcesFromMapping(mapping)) {
					addResource(r, result);
				}
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
	private static Repository getRepository(@NonNull IProject project) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(project);
		if (mapping != null) {
			return mapping.getRepository();
		}
		return org.eclipse.egit.core.Activator.getDefault().getRepositoryCache()
				.getRepository(project);
	}

