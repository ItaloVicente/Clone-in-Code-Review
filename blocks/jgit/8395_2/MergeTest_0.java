
	@Test
	public void testNoFastForward() throws Exception {
		git.branchCreate().setName("side").call();
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		git.commit().setMessage("commit").call();
		git.checkout().setName("side").call();

		assertEquals("Merge made by the 'recursive' strategy."
				execute("git merge master --no-ff")[0]);
		assertArrayEquals(new String[] {
				"commit 6db23724012376e8407fc24b5da4277a9601be81"
				"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
				"Date:   Sat Aug 15 20:12:58 2009 -0330"
				""
				"    Merge branch 'master' into side"
				""
				"commit 6fd41be26b7ee41584dd997f665deb92b6c4c004"
				"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
				"Date:   Sat Aug 15 20:12:58 2009 -0330"
				""
				"    initial commit"
				""
				"commit 26a81a1c6a105551ba703a8b6afc23994cacbae1"
				"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
				"Date:   Sat Aug 15 20:12:58 2009 -0330"
				""
				"    commit"
				""
				""
		}
	}

	@Test
	public void testNoFastForwardAndSquash() throws Exception {
		assertEquals("You cannot combine --squash with --no-ff."
				execute("git merge master --no-ff --squash")[0]);
	}

	@Test
	public void testFastForwardOnly() throws Exception {
		git.branchCreate().setName("side").call();
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		git.commit().setMessage("commit#1").call();
		git.checkout().setName("side").call();
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		git.commit().setMessage("commit#2").call();

		assertEquals("fatal: Not possible to fast-forward
				execute("git merge master --ff-only")[0]);
	}
