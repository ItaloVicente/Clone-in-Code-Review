	@Test
	public void nonexistantExtensionNotDeleted() throws Exception {
		createFileInPackFolder(NONEXISTENT_EXT);
		gc.gc().get();
		assertTrue(new File(packDir
	}

