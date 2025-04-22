	@Test
	public void testPruneOldPacksWithOpenHandleOnPack() throws Exception {
		gc.setExpireAgeMillis(0);

		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		fsTick();
		gc.gc();

		Collection<PackFile> packs = repo.getObjectDatabase().getPacks();
		assertEquals(1
		PackFile pack = packs.iterator().next();
		File packFile = pack.getPackFile();
		File indexFile = new File(packFile.getParentFile()
				+ pack.getPackName()
				+ "."
				+ PackExt.INDEX.getExtension());
		FileInputStream fis = new FileInputStream(packFile);
		try {
			bb.commit().add("A"
			fsTick();
			gc.gc();
			if (packFile.exists()) {
				assertTrue(
						"The pack was present but the index file was missing."
						indexFile.exists());
			}

		} finally {
			fis.close();
		}

	}

