
	@Test
	public void testApplyStashWithDeletedFile() throws Exception {
		File file = writeTrashFile("file"
		git.add().addFilepattern("file").call();
		git.commit().setMessage("x").call();
		file.delete();
		git.rm().addFilepattern("file").call();
		git.stashCreate().call();
		file.delete();

		git.stashApply().setStashRef("stash@{0}").call();

		assertFalse(file.exists());
	}
