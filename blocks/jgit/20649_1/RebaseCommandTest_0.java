	@Test
	public void testFastForwardRebaseWithAutoStash() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"

		assertEquals(Status.FAST_FORWARD
				git.rebase().setUpstream("refs/heads/master")
				.call().getStatus());
		checkFile(new File(db.getWorkTree()
				"unstaged modified file0");
		checkFile(new File(db.getWorkTree()
		assertEquals("[file0
				+ "[file1
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
	}

