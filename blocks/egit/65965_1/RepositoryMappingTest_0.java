	@Test
	public void shouldResolveRelativePathRelativeToContainer() {
		IPath projectPath = project.getProject().getLocation();
		RepositoryMapping mapping = RepositoryMapping
				.create(project.getProject(), new File(".git"));
		assertEquals(projectPath.append(".git"),
				mapping.getGitDirAbsolutePath());
	}

