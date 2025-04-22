	@Test
	public void shouldReturnMappingWhenPathIsRepository() {
		IPath workTreePath = getWorkTreePath();

		RepositoryMapping mapping = RepositoryMapping.getMapping(workTreePath);

		assertNotNull(mapping);
		assertEquals(repository, mapping.getRepository());
		assertEquals("", mapping.getRepoRelativePath(workTreePath));
	}

