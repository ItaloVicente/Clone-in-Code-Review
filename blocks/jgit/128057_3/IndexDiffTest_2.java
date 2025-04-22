	@Test
	public void testMissing() throws Exception {
		File file2 = writeTrashFile("file2"
		File file3 = writeTrashFile("dir/file3"
		Git git = Git.wrap(db);
		git.add().addFilepattern("file2").addFilepattern("dir/file3").call();
		git.commit().setMessage("commit").call();
		assertTrue(file2.delete());
		assertTrue(file3.delete());
		IndexDiff diff = new IndexDiff(db
				new FileTreeIterator(db));
		diff.diff();
		assertEquals(2
		assertTrue(diff.getMissing().contains("file2"));
		assertTrue(diff.getMissing().contains("dir/file3"));
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(Collections.EMPTY_SET
	}

