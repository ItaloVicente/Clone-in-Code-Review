	@Test
	public void testGetProjectsContains() throws Exception {
		IProject[] projects = ProjectUtil.getValidOpenProjects(repository
				.getRepository());
		repository.createFile(projects[0], "xxx");
		ProjectUtil.refreshValidProjects(projects, new NullProgressMonitor());
		IProject[] projectsContaining = ProjectUtil.getProjectsContaining(
				repository.getRepository(),
				Collections.singleton("Project-1/xxx"));
		IProject[] projectsEmpty = ProjectUtil.getProjectsContaining(
				repository.getRepository(), Collections.singleton("yyy"));

		assertEquals(1, projectsContaining.length);
		assertEquals(0, projectsEmpty.length);

		IProject p = projectsContaining[0];
		assertEquals("Project-1", p.getDescription().getName());
	}

