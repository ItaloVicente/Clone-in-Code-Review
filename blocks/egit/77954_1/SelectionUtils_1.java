	@NonNull
	private static Set<Repository> getRepositoriesFromSelection(
			@NonNull IStructuredSelection selection) {
		Set<Repository> result = new HashSet<>();
		for (Object o : selection.toList()) {
			Repository repo = AdapterUtils.adapt(o, Repository.class);
			if (repo != null) {
				result.add(repo);
				continue;
			}
			IResource resource = AdapterUtils.adapt(o, IResource.class);
			if (resource != null) {
				repo = ResourceUtil.getRepository(resource);
				if (repo != null) {
					result.add(repo);
				}
				continue;
			}
			IPath location = AdapterUtils.adapt(o, IPath.class);
			if (location != null) {
				repo = ResourceUtil.getRepository(location);
				if (repo != null) {
					result.add(repo);
				}
			}
		}
		return result;
	}

