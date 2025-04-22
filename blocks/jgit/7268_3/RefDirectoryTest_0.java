	@Test
	public void testReadNotExistingBranchConfig() throws IOException {
		assertNull("find branch config"
		assertNull("find branch config"
	}

	@Test
	public void testReadBranchConfig() throws IOException {
		writeLooseRef("refs/heads/config"

		assertNotNull("find branch config"
	}

