	@Test
	public void testGetProjectsContains() throws Exception {
		TestProject prj2 = new TestProject(true, "Project-1-sub");

		try {
			repository.createFile(project.getProject(), "xxx");
			repository.createFile(project.getProject(), "zzz");
			repository.createFile(prj2.getProject(), "zzz");

			project.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
			prj2.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);

			IProject[] projectsContaining = ProjectUtil.getProjectsContaining(
					repository.getRepository(),
					Collections.singleton("Project-1/xxx"));
			IProject[] projectsEmpty = ProjectUtil.getProjectsContaining(
					repository.getRepository(), Collections.singleton("yyy"));
			IProject[] projectSelf = ProjectUtil.getProjectsContaining(
					repository.getRepository(),
					Collections.singleton("Project-1"));
			Set<String> files = new TreeSet<String>();
			files.add("Project-1/xxx");
			files.add("Project-1/zzz");
			IProject[] multiFile = ProjectUtil.getProjectsContaining(
					repository.getRepository(), files);

			files.clear();
			files.add("Project-1/xxx");
			files.add("Project-1-sub/zzz");
			IProject[] multiProject = ProjectUtil.getProjectsContaining(
					repository.getRepository(), files);
			IProject[] nonExistProject = ProjectUtil.getProjectsContaining(
					repository.getRepository(),
					Collections.singleton("Project-2"));

			assertEquals(1, projectsContaining.length);
			assertEquals(0, projectsEmpty.length);
			assertEquals(1, projectSelf.length);
			assertEquals(1, multiFile.length);
			assertEquals(2, multiProject.length);
			assertEquals(0, nonExistProject.length);

			IProject p = projectsContaining[0];
			assertEquals("Project-1", p.getDescription().getName());
		} finally {
			prj2.dispose();
		}
	}

	@Test
	public void testFindContainer() throws Exception {
		File tmp = new File("/tmp/file");
		File test1 = new File(project.getProject().getLocation().toFile(), "xxx");
		File test2 = new File(repository.getRepository().getWorkTree(), "xxx");

		assertNull(ProjectUtil.findContainer(tmp));
		assertEquals(project.getProject(), ProjectUtil.findContainer(test1));
		assertEquals(ResourcesPlugin.getWorkspace().getRoot(), ProjectUtil.findContainer(test2));
	}

