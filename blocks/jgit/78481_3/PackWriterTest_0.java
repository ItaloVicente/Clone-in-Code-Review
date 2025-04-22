		assertContent(idx
				contentA.getId()

		idx = writeShallowPack(repo
		assertContent(idx
				contentC.getId()
	}

	@Test
	public void testShallowIsMinimalDepth2() throws Exception {
		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo
