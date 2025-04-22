	public void testAssumeUnchanged() throws Exception {
		Git git = new Git(db);
		String path = "file";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		String path2 = "file2";
		writeTrashFile(path2
		git.add().addFilepattern(path2).call();
		git.commit().setMessage("commit").call();
		assumeUnchanged(path2);
		writeTrashFile(path
		writeTrashFile(path2

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertEquals(1
		assertEquals(2
		assertEquals(0

		git.add().addFilepattern(".").call();

		iterator = new FileTreeIterator(db);
		diff = new IndexDiff(db
		diff.diff();
		assertEquals(1
		assertEquals(1
		assertEquals(1
		assertTrue(diff.getAssumeUnchanged().contains("file2"));
		assertTrue(diff.getModified().contains("file2"));
		assertTrue(diff.getChanged().contains("file"));
	}

