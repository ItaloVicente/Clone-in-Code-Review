		super.tearDown();


		RepositoryProvider.unmap(project);

		GitProjectData.delete(project);

		project.delete(true, true, null);

		repository.close();

		Activator.getDefault().getRepositoryCache().clear();

		recursiveDelete(gitDir);
