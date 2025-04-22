		for (IResource resource : resources) {
			if (resource.getType() == IResource.ROOT) {
				GitSyncCache newCache = GitSyncCache.getAllData(gsds, monitor);
				cache.merge(newCache);
				super.refresh(resources, depth, monitor);
				return;
			}
		}

		List<String> paths = new ArrayList<String>();
		for (IResource resource : resources) {
			IProject project = resource.getProject();
			if (gsds.contains(project)) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(project);
				if (mapping != null) {
					String path = mapping.getRepoRelativePath(resource);
					if (path.length() != 0)
						paths.add(path);
				}
			}
		}

		if (!paths.isEmpty()) {
			GitSyncCache newCache = GitSyncCache.getAllData(gsds, paths,
					monitor);
			cache.merge(newCache);
		}
