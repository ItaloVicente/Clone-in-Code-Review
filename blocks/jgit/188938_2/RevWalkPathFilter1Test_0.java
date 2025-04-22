
	@Test
	public void testStopWhenPathDisappears() throws Exception {
		DirCacheEntry file1 = file("src/d1/file1"
		DirCacheEntry file2 = file("src/d1/file2"
		DirCacheEntry file3 = file("src/d1/file3"
		RevCommit a = commit(tree(file1));
		RevCommit b = commit(tree(file1
		RevCommit c = commit(tree(file1
		RevCommit d = commit(tree(file1
		filter("src/d1");
		markStart(d);
		rw.setRewriteParents(false);

		assertCommit(d
		assertCommit(c
		assertCommit(b
		assertCommit(a
	}
