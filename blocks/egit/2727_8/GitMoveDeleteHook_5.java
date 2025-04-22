
				IProject destination = source.getWorkspace().getRoot()
						.getProject(description.getName());
				GitProjectData projectData = new GitProjectData(destination);
				RepositoryMapping repositoryMapping = new RepositoryMapping(
						destination, gitDir.toFile());
				projectData.setRepositoryMappings(Arrays
						.asList(repositoryMapping));
				projectData.store();
				GitProjectData.add(destination, projectData);
				RepositoryProvider.map(destination, GitProvider.class.getName());
				destination.refreshLocal(IResource.DEPTH_INFINITE,
						new SubProgressMonitor(monitor, 50));
