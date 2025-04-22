
	@Test
	@SuppressWarnings("null")
	public void testCherryPickDirtyIndex() throws Exception {
		Git git = new Git(db);

		File a = writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit firstMasterCommit = git.commit().setMessage("first master")
				.call();

		createBranch(firstMasterCommit
		checkoutBranch("refs/heads/side");
		writeTrashFile("c"
		git.add().addFilepattern("c").call();
		git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		RevCommit secondMasterCommit = git.commit().setMessage("second master")
				.call();

		checkoutBranch("refs/heads/side");
		write(a
		git.add().addFilepattern("a").call();

		String indexState = indexState(MOD_TIME | SMUDGE | LENGTH | CONTENT_ID
				| CONTENT | ASSUME_UNCHANGED);

		AbnormalMergeFailureException exception = null;
		try {
			git.cherryPick().include(secondMasterCommit.getId()).call();
		} catch (AbnormalMergeFailureException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals(1
		assertEquals(MergeFailureReason.DIRTY_INDEX
				.getFailingPaths().get("a"));
		assertEquals("a_"
		assertEquals(indexState
				| CONTENT_ID | CONTENT | ASSUME_UNCHANGED));
		assertEquals(RepositoryState.SAFE
	}
