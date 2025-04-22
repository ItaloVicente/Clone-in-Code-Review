
	@Test
	public void testLogAll() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("file1 commit").call();

		assertArrayEquals(
				new String[]{"commit 3d5ad5dc2c2ab06cd5e35109019d834dccf6b3f5"
						"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
						"Date:   Sat Aug 15 20:12:58 2009 -0330"
						""
						"    file1 commit"
						""
						"commit 6fd41be26b7ee41584dd997f665deb92b6c4c004"
						"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
						"Date:   Sat Aug 15 20:12:58 2009 -0330"
						""
						"    initial commit"
						""
						""}
				execute("git log --all"));
	}

	@Test
	public void testLogAllNot() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();

		assertArrayEquals(
				new String[]{""}
				execute("git log --not --all"));
	}

	@Test
	public void testLogNot() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
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
				new String[] { "commit 9121e0c521860990acdeb8c64f493f94c609689a"
						"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
						"Date:   Sat Aug 15 20:12:58 2009 -0330"
						""
						"    side commit"
						""
						""}
				execute("git log --not master --not side"));
	}

