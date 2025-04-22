	@Test
	public void testReadPackedRefs_ReloadPackedRefListByDefault() throws IOException {
				v1_0.name() + " refs/tags/v1.0\n");
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

				v1_0.name() + " refs/tags/v1.0\n");
		try (FileRepository testRepository = new FileRepository(diskRepo.getDirectory())) {
			RefDirectory testRefDir = (RefDirectory) testRepository.getRefDatabase();

			RefDirectory.PackedRefList packedRefsList = testRefDir.getPackedRefs();
			RefDirectory.PackedRefList readPackedRefsList = testRefDir.readPackedRefs(packedRefsList);

			assertEquals(readPackedRefsList.getId()
			assertSame(packedRefsList
		}
	}

