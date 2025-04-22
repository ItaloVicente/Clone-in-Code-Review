
	@Test
	public void testDirFilter_MergeNonOverlappingCommits() throws Exception {
		RevCommit v0 = commit(tree(file("a/b/README"
		RevCommit x0 = commit(tree(file("a/b/README"

		RevCommit v1 = commit(tree(
				file("a/b/LICENSE"
				file("a/b/HACKING"

		RevCommit merge = commit(tree(file("a/b/README"
				file("a/b/LICENSE"
				file("a/b/HACKING"

		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("a/b")
				TreeFilter.ANY_DIFF));

		markStart(merge);
		assertCommit(merge
		assertCommit(v1
		assertCommit(v0
		assertCommit(null
	}

	@Test
	public void testDirFilter_MergeOneParentDoesntContribute() throws Exception {
		RevCommit v0 = commit(tree(
				file("a/b/README"
				file("a/b/LICENSE"
				file("a/b/HACKING"

		RevCommit v1 = commit(tree(
				file("a/b/README"
				file("a/b/LICENSE"

		RevCommit merge = commit(tree(file("a/b/README"
				file("a/b/LICENSE"
				file("a/b/HACKING"

		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("a/b")
				TreeFilter.ANY_DIFF));

		markStart(merge);

		assertCommit(v0
		assertCommit(null
	}
