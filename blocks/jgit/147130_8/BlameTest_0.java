
	@Test
	public void testBlameMergeConflict() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file"
			git.add().addFilepattern("file").call();
			git.commit().setMessage("initial commit").call();
			git.checkout().setCreateBranch(true)
					.setName("side").call();
			writeTrashFile("file"
					"Conflicting change from side branch\n");
			git.add().addFilepattern("file").call();
			RevCommit side = git.commit().setMessage("side commit").call();
			git.checkout().setName(Constants.MASTER).call();
			writeTrashFile("file"
			git.add().addFilepattern("file").call();
			git.commit().setMessage("Commit conflict on master").call();
			MergeResult result = git.merge()
					.include("side"
			assertTrue("Expected conflict on 'file'"
					result.getConflicts().containsKey("file"));
		}
		String[] expected = {
				"         (Not Committed Yet                            1) <<<<<<< HEAD"
				"7a918de5 (GIT_COMMITTER_NAME 2009-08-15 20:12:58 -0330 2) Change on master branch"
				"         (Not Committed Yet                            3) ======="
				"beb52f68 (GIT_COMMITTER_NAME 2009-08-15 20:12:58 -0330 4) Conflicting change from side branch"
				"         (Not Committed Yet                            5) >>>>>>> side" };
		assertArrayOfLinesEquals(expected
	}
