	@Test
	public void testDetectDeletedFile() throws Exception {
		Git git = Git.wrap(db);
		File file = writeTrashFile("file"
		git.add().addFilepattern(".").call();
		RevCommit commit = git.commit().setMessage("").call();
		file.delete();
		writeTrashFile("unrelated"
		List<DiffEntry> entries = scanTrees(new FileTreeIterator(db)
				commit.getTree());

		rd.addAll(entries);
		List<DiffEntry> returnedEntries = rd.compute();

		assertEquals(2
		assertSame(entries.get(0)
		assertSame(entries.get(1)
	}

	@Test
	public void testDetectUnstagedRename() throws Exception {
		Git git = Git.wrap(db);
		File file = writeTrashFile("file"
		git.add().addFilepattern(".").call();
		RevCommit commit = git.commit().setMessage("").call();
		file.delete();
		writeTrashFile("renamed-file"
		List<DiffEntry> entries = scanTrees(new FileTreeIterator(db)
				commit.getTree());

		rd.addAll(entries);
		List<DiffEntry> returnedEntries = rd.compute();

		assertEquals(1
		assertRename(entries.get(0)
				returnedEntries.get(0));
	}

	private List<DiffEntry> scanTrees(FileTreeIterator p
			throws IOException {
		try (TreeWalk treeWalk = new TreeWalk(db);) {
			treeWalk.addTree(tree);
			treeWalk.addTree(p);
			return DiffEntry.scan(treeWalk
		}
	}

