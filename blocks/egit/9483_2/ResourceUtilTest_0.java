
	@Test
	public void getFileForLocationShouldReturnExistingFileInCaseOfNestedProjectWithClosedRoot() throws Exception {
		TestProject nested = new TestProject(true, "Project-1/Project-2");
		IFile file = nested.createFile("a.txt", new byte[] {});
		IPath location = file.getLocation();
		project.getProject().close(null);

		IFile result = ResourceUtil.getFileForLocation(location);
		assertThat(result, notNullValue());
		assertTrue("Returned IFile should exist", result.exists());
		assertThat(result.getProject(), is(nested.getProject()));
	}
