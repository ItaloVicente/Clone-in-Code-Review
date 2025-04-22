
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
			RevCommit side = git.commit().setMessage("side commit")
					.setCommitter(new PersonIdent("gitter"
			git.checkout().setName(Constants.MASTER).call();
			writeTrashFile("file"
			git.add().addFilepattern("file").call();
			git.commit().setMessage("Commit conflict on master")
					.setCommitter(new PersonIdent("gitter"
			MergeResult result = git.merge()
					.include("side"
			assertTrue("Expected conflict on 'file'"
					result.getConflicts().containsKey("file"));
		}
		String[] expected = {
				"00000000 (Not Committed Yet 2009-08-15 20:12:58 -0330 1) <<<<<<< HEAD"
				"0f5b671c (gitter            2009-08-15 20:12:58 -0330 2) Change on master branch"
				"00000000 (Not Committed Yet 2009-08-15 20:12:58 -0330 3) ======="
				"ae78cff6 (gitter            2009-08-15 20:12:58 -0330 4) Conflicting change from side branch"
				"00000000 (Not Committed Yet 2009-08-15 20:12:58 -0330 5) >>>>>>> side" };
		assertArrayOfLinesEquals(expected
	}
