	@Test public void shouldActAsResourceProvider()
			throws Exception {
		GitModelBlob left = createGitModelBlob();

		IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder/test.txt"));
		IPath leftLocation = left.getResource().getLocation();
		assertEquals(file.getLocation(), leftLocation);
	}
	
