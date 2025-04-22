
	@Test
	public void getFileForLocationShouldReturnExistingFileInCaseOfNestedNotClosedProject()
			throws Exception {
		TestProject nested = new TestProject(true, "Project-1/Project-0");
		TestProject nested2 = new TestProject(true,
				"Project-1/Project-0/Project");
		IFile file = nested2.createFile("a.txt", new byte[] {});
		IPath location = file.getLocation();
		nested2.project.close(new NullProgressMonitor());
		IFile result = ResourceUtil.getFileForLocation(location);
		assertThat(result, notNullValue());
		assertTrue("Returned IFile should exist", result.exists());
		assertThat(result.getProject(), is(nested.getProject()));
	}
