
	@Test
	public void testSquash() throws Exception {
		git.branchCreate().setName("side").call();
		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("file1 commit").call();
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("file2 commit").call();
		git.checkout().setName("side").call();
		writeTrashFile("side"
		git.add().addFilepattern("side").call();
		git.commit().setMessage("side commit").call();

		assertArrayEquals(
				new String[] { "Squash commit -- not updating HEAD"
						"Automatic merge went well; stopped before committing as requested"
						"" }
				execute("git merge master --squash"));
	}
