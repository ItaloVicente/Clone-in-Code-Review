	@Test
	public void testHardResetAfterSquashMerge() throws Exception {
		Git g = new Git(db);

		writeTrashFile("file1"
		g.add().addFilepattern("file1").call();
		RevCommit first = g.commit().setMessage("initial commit").call();

		assertTrue(new File(db.getWorkTree()
		createBranch(first
		checkoutBranch("refs/heads/branch1");

		writeTrashFile("file2"
		g.add().addFilepattern("file2").call();
		g.commit().setMessage("second commit").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/master");

		MergeResult result = g.merge().include(db.getRef("branch1"))
				.setSquash(true).call();

		assertEquals(MergeResult.MergeStatus.FAST_FORWARD_SQUASHED
				result.getMergeStatus());
		assertNotNull(db.readSquashCommitMsg());

		g.reset().setMode(ResetType.HARD).setRef(first.getName()).call();

		assertNull(db.readSquashCommitMsg());
	}

