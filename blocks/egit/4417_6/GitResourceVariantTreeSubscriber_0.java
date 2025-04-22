		for (IResource resource : resources) {
			if (resource.getType() == IResource.ROOT) {
				GitSyncCache newCache = GitSyncCache.getAllData(gsds, monitor);
				cache.merge(newCache);
				super.refresh(resources, depth, monitor);
				return;
			}
		}

		Map<GitSynchronizeData, Collection<String>> updateRequests = new HashMap<GitSynchronizeData, Collection<String>>();
		for (IResource resource : resources) {
			IProject project = resource.getProject();
			GitSynchronizeData data = gsds.getData(project.getName());
			if (data != null) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(project);
				if (mapping != null) {
					Collection<String> paths = updateRequests.get(data);
					if (paths == null) {
						paths = new ArrayList<String>();
						updateRequests.put(data, paths);
					}

					String path = mapping.getRepoRelativePath(resource);
					if (path == null)
						path = ""; //$NON-NLS-1$
					paths.add(path);
				}
			}
		}

		if (!updateRequests.isEmpty()) {
			GitSyncCache newCache = GitSyncCache.getAllData(updateRequests,
					monitor);
			cache.merge(newCache);
		}
