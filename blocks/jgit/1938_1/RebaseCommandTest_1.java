	public void testUpToDate() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit first = git.commit().setMessage("Add file1").call();

		assertTrue(new File(db.getWorkTree()

		RebaseResult res = git.rebase().setUpstream(first).call();
		assertEquals(Status.UP_TO_DATE
	}

	public void testUnknownUpstream() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("Add file1").call();

		assertTrue(new File(db.getWorkTree()

		try {
			git.rebase().setUpstream("refs/heads/xyz")
					.call();
			fail("expected exception was not thrown");
		} catch (RefNotFoundException e) {
		}
	}

