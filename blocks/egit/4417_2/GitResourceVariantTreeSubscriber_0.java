		IResource[] toScan = resources;
		for (IResource resource : resources) {
			if (resource.getType() == IResource.ROOT) {
				GitSyncCache newCache = GitSyncCache.getAllData(gsds, monitor);
				cache.merge(newCache);
				super.refresh(resources, depth, monitor);
				return;
			}
		}

		List<String> paths = new ArrayList<String>();
		for (IResource resource : toScan) {
			IProject project = resource.getProject();
			if (gsds.contains(project)) {
				GitSynchronizeData data = gsds.getData(project);
				if (data != null) {
					paths.add(RepositoryMapping.getMapping(project)
							.getRepoRelativePath(resource));
				}
			}
		}

		if (!paths.isEmpty()) {
			GitSyncCache newCache = GitSyncCache.getAllData(gsds, paths,
					monitor);
			cache.merge(newCache);
		}
