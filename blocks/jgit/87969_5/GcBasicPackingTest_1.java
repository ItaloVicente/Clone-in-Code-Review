	@Test
	public void testPreserveAndPruneOldPacks() throws Exception {
		testPreserveOldPacks();
		configureGc(gc
		gc.gc();

		assertFalse(repo.getObjectDatabase().getPreservedDirectory().exists());
	}

	private void testPreserveOldPacks() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().message("P").add("P"

		gc.setExpireAgeMillis(0);
		gc.gc();
		File oldPackfile = tr.getRepository().getObjectDatabase().getPacks()
				.iterator().next().getPackFile();
		assertTrue(oldPackfile.exists());

		fsTick();
		bb.commit().message("B").add("B"

		gc.setPackExpireAgeMillis(0);
		configureGc(gc
		gc.gc();

		File oldPackDir = repo.getObjectDatabase().getPreservedDirectory();
		String oldPackFileName = oldPackfile.getName();
		String oldPackName = oldPackFileName.substring(0
		File preservePackFile = new File(oldPackDir
		assertTrue(preservePackFile.exists());
	}

	private PackConfig configureGc(GC myGc
