	@Test
	public void testMultiRenameStartInbetween() throws Exception {
		final String contents = "A";
		final RevCommit a = commit(tree(file("a"

		CommitBuilder commitBuilder = commitBuilder().parent(a)
				.add("b"
		RevCommit renameCommit1 = commitBuilder.create();

		commitBuilder = commitBuilder().parent(renameCommit1)
				.add("c"
		RevCommit renameCommit2 = commitBuilder.create();

		commitBuilder = commitBuilder().parent(renameCommit2)
				.add("a"
		commitBuilder.create();

		follow("a");
		markStart(renameCommit2);
		assertCommit(renameCommit2
		assertRenames("b->c");
		assertCommit(renameCommit1
		assertRenames("b->c"
		assertCommit(a
		assertRenames("b->c"
		assertNull(rw.next());
