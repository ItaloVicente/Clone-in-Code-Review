		RepositoryMapping repositoryMapping = RepositoryMapping.create(destination, gitDir.toFile());
		if (repositoryMapping != null) {
			GitProjectData projectData = new GitProjectData(destination);
			projectData.setRepositoryMappings(Arrays.asList(repositoryMapping));
			projectData.store();
			GitProjectData.add(destination, projectData);
			RepositoryProvider.map(destination, GitProvider.class.getName());
			destination.refreshLocal(IResource.DEPTH_INFINITE,
					new SubProgressMonitor(monitor, 50));
		}
