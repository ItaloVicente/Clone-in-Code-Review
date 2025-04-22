	@Test
	public void testFindProjectFilesNested() throws Exception {
		project2 = new TestProject(true, "Project-1/Project-Nested");
		File workingDir = gitDir.getParentFile();

		Collection<File> foundFiles = new ArrayList<File>();
		boolean found = ProjectUtil.findProjectFiles(foundFiles, workingDir,
				null, new NullProgressMonitor());

		assertTrue("Expected to find projects", found);
		assertThat(foundFiles, hasItem(new File(workingDir, "Project-1/.project")));
		assertThat(foundFiles, hasItem(new File(workingDir, "Project-1/Project-Nested/.project")));
	}
