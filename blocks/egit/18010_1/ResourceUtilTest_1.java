
	@Test
	public void getFileForLocationShouldNotUseFilesWithoutRepositoryMapping()
			throws Exception {
		TestProject nested = new TestProject(true, "Project-1/Project-0");
		IFile file = nested.createFile("a.txt", new byte[] {});
		IPath location = file.getLocation();

		IFile result = ResourceUtil.getFileForLocation(location);
		assertThat(result, notNullValue());
		assertTrue("Returned IFile should exist", result.exists());
		assertThat(result.getProject(), is(project.getProject()));
	}

	private void connect(IProject p) throws CoreException {
		ConnectProviderOperation operation = new ConnectProviderOperation(p,
				gitDir);
		operation.execute(null);
	}
