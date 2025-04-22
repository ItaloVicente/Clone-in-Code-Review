	@Test
	public void shouldNotReturnMappingWhenPathIsOutsideRepository() {
		IPath workTreePath = getWorkTreePath();

		assertNull(RepositoryMapping
				.getMapping(new Path("D:/some/made/up/path")));
		assertNull(RepositoryMapping.getMapping(new Path("/some/made/up/path")));
		assertNull(RepositoryMapping.getMapping(new Path(
				"/thershouldnever/be/something/here")));

		if (workTreePath.getDevice() == null)
			assertNull(RepositoryMapping.getMapping(workTreePath
					.setDevice("C:")));
	}

