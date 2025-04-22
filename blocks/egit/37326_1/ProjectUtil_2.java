		IPath repositoryPath = new Path(repository.getWorkTree().getAbsolutePath());
		IProject[] projects = null;
		Set<IResource> resources = new LinkedHashSet<IResource>();
		for (String relativePath : relativePaths) {
			IPath location = repositoryPath.append(relativePath);
			IResource resource = ResourceUtil
					.getResourceForLocation(location);
			if (resource != null) {
				resources.add(resource);
			} else {
				if (projects == null)
					projects = getProjects(repository);
				for (IProject project : projects) {
					IPath projectLocation = project.getLocation();
					if (projectLocation != null
							&& location.isPrefixOf(projectLocation))
						resources.add(project);
				}
			}
		}
		refreshResources(resources.toArray(new IResource[0]), monitor);
