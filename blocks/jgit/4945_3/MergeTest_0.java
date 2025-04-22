	@Test
	public void testMergeNoCommit() throws Exception {
		git.branchCreate().setName("side").call();
		writeTrashFile("master"
		git.add().addFilepattern("master").call();
		git.commit().setMessage("master commit").call();
		git.checkout().setName("side").call();
		writeTrashFile("side"
		git.add().addFilepattern("side").call();
		git.commit().setMessage("side commit").call();

		assertEquals(
				"Automatic merge went well; stopped before committing as requested"
				execute("git merge --no-commit master")[0]);
	}

	@Test
	public void testMergeNoCommitSquash() throws Exception {
		git.branchCreate().setName("side").call();
		writeTrashFile("master"
		git.add().addFilepattern("master").call();
		git.commit().setMessage("master commit").call();
		git.checkout().setName("side").call();
		writeTrashFile("side"
		git.add().addFilepattern("side").call();
		git.commit().setMessage("side commit").call();

		assertArrayEquals(
				new String[] {
						"Squash commit -- not updating HEAD"
						"Automatic merge went well; stopped before committing as requested"
						"" }
	}

