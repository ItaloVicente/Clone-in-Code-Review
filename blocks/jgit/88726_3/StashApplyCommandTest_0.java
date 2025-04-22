
	@Test
	public void untrackedAndTrackedChanges() throws Exception {
		writeTrashFile(PATH
		String path = "untracked.txt";
		writeTrashFile(path
		git.stashCreate().setIncludeUntracked(true).call();
		assertTrue(PATH + " should exist"
		assertEquals(PATH + " should have been reset"
		assertFalse(path + " should not exist"
		git.stashApply().setStashRef("stash@{0}").call();
		assertTrue(PATH + " should exist"
		assertEquals(PATH + " should have new content"
		assertTrue(path + " should exist"
		assertEquals(path + " should have new content"
				read(path));
	}
