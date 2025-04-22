	@Test
	public void testCheckoutOursWhenNoBase() throws Exception {
		String file = "added.txt";

		git.checkout().setCreateBranch(true).setName("side")
				.setStartPoint(initialCommit).call();
		writeTrashFile(file
		git.add().addFilepattern(file).call();
		RevCommit side = git.commit().setMessage("Commit on side").call();

		git.checkout().setName("master").call();
		writeTrashFile(file
		git.add().addFilepattern(file).call();
		git.commit().setMessage("Commit on master").call();

		git.merge().include(side).call();
		assertEquals(RepositoryState.MERGING

		DirCache cache = DirCache.read(db.getIndexFile()
		assertEquals("Expected add/add file to not have base stage"
				DirCacheEntry.STAGE_2

		git.checkout().setStage(Stage.OURS).addPath(file).call();

		assertEquals("Added on master"

		cache = DirCache.read(db.getIndexFile()
		assertEquals("Expected conflict stages to still exist after checkout"
				DirCacheEntry.STAGE_2
	}

