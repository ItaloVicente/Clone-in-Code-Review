	@NonNull
	private static Set<Object> getSelectionContents(
			@NonNull IStructuredSelection selection) {
		Set<Object> result = new HashSet<>();
		for (Object o : selection.toList()) {
			IResource resource = AdapterUtils.adapt(o, IResource.class);
			if (resource != null) {
				IProject project = resource.getProject();
				if (project != null) {
					result.add(project);
					continue;
				} else {
					IPath path = resource.getLocation();
					if (path != null) {
						result.add(path);
						continue;
					}
				}
			}
			ResourceMapping mapping = AdapterUtils.adapt(o,
					ResourceMapping.class);
			if (mapping != null) {
				for (IResource r : extractResourcesFromMapping(mapping)) {
					IProject project = r.getProject();
					if (project != null) {
						result.add(project);
					} else {
						IPath path = r.getLocation();
						if (path != null) {
							result.add(path);
						}
					}
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

