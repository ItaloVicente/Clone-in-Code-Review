	@Test
	public void testTwoRenames() throws Exception {
		Git git = new Git(db);

		String[] content1 = new String[] { "a" };
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		writeTrashFile("file1.txt"
		git.add().addFilepattern("file1.txt").call();
		git.rm().addFilepattern("file.txt").call();
		git.commit().setMessage("moving file").call();

		String[] content2 = new String[] { "a"
		writeTrashFile("file1.txt"
		git.add().addFilepattern("file1.txt").call();
		RevCommit commit3 = git.commit().setMessage("editing file").call();

		writeTrashFile("file2.txt"
		git.add().addFilepattern("file2.txt").call();
		git.rm().addFilepattern("file1.txt").call();
		git.commit().setMessage("moving file again").call();

		BlameCommand command = new BlameCommand(db);
		command.setFollowFileRenames(true);
		command.setFilePath("file2.txt");
		BlameResult lines = command.call();

		assertEquals(commit1
		assertEquals(0
		assertEquals("file.txt"

		assertEquals(commit3
		assertEquals(1
		assertEquals("file1.txt"
	}

