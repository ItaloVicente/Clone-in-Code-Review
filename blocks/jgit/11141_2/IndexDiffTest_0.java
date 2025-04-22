	@Test
	public void testUntrackedNotIgnoredFolders() throws Exception {
		Git git = new Git(db);

		IndexDiff diff = new IndexDiff(db
				new FileTreeIterator(db));
		diff.diff();
		assertEquals(Collections.EMPTY_SET

		writeTrashFile("readme"
		writeTrashFile("sr/com/X.java"
		writeTrashFile("src/com/A.java"
		writeTrashFile("src/org/B.java"
		writeTrashFile("srcs/org/Y.java"
		writeTrashFile("target/com/A.java"
		writeTrashFile("target/org/B.java"
		writeTrashFile(".gitignore"

		git.add().addFilepattern("readme").addFilepattern(".gitignore")
				.addFilepattern("srcs/").call();
		git.commit().setMessage("initial").call();

		diff = new IndexDiff(db
		diff.diff();
		assertEquals(new HashSet<String>(Arrays.asList("src"))
				diff.getUntrackedFolders());

		git.add().addFilepattern("src").call();
		writeTrashFile("sr/com/X1.java"
		writeTrashFile("src/tst/A.java"
		writeTrashFile("src/tst/B.java"
		writeTrashFile("srcs/com/Y1.java"
		deleteTrashFile(".gitignore");

		diff = new IndexDiff(db
		diff.diff();
		assertEquals(
				new HashSet<String>(Arrays.asList("srcs/com"
						"target"))
				diff.getUntrackedFolders());
	}

