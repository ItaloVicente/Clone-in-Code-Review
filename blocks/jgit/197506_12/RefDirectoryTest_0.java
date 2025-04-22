	@Test
	public void testReadPackedRefs_ReloadPackedRefListByDefault() throws IOException {
		writePackedRefs(A.name() + " refs/heads/master\n");
		RefDirectory testRefDir = (RefDirectory) refdir.getRepository().getRefDatabase();

		RefDirectory.PackedRefList packedRefsList = testRefDir.getPackedRefs();
		RefDirectory.PackedRefList readPackedRefsList = testRefDir.readPackedRefs(packedRefsList);

		assertEquals(readPackedRefsList.getId()
		assertNotSame(packedRefsList
	}

	@Test
	public void testReadPackedRefs_CompareByContentAvoidReloadPackedRefList() throws IOException {
		StoredConfig repoConfig = refdir.getRepository().getConfig();
		repoConfig.setBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_COMPARE_PACKED_REFS_BY_SHA1
		repoConfig.save();

		writePackedRefs(A.name() + " refs/heads/master\n");
		try (FileRepository testRepository = new FileRepository(diskRepo.getDirectory())) {
			RefDirectory testRefDir = (RefDirectory) testRepository.getRefDatabase();

			RefDirectory.PackedRefList packedRefsList = testRefDir.getPackedRefs();
			RefDirectory.PackedRefList readPackedRefsList = testRefDir.readPackedRefs(packedRefsList);

			assertEquals(readPackedRefsList.getId()
			assertSame(packedRefsList
		}
	}

