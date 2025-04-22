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
				String path = RepositoryMapping.getMapping(project)
						.getRepoRelativePath(resource);
				if (!path.equals("")) { //$NON-NLS-1$
					paths.add(path);
				}
			}
		}

		if (!paths.isEmpty()) {
			GitSyncCache newCache = GitSyncCache.getAllData(gsds, paths,
					monitor);
			cache.merge(newCache);
		}
