	@Test
	public void testMaxLimit() throws Exception {
		try (Git git = new Git(db); TreeWalk walk = new TreeWalk(db)) {
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			FileUtils.mkdir(new File(db.getWorkTree()
			writeTrashFile("a.txt"
			writeTrashFile("b/1.txt"
			writeTrashFile("b/2.txt"
			writeTrashFile("c.txt"
			git.add().addFilepattern("a.txt").addFilepattern("b")
					.addFilepattern("c.txt").call();
			RevCommit c2 = git.commit().setMessage("second commit").call();
			TreeFilter filterA = PathFilterGroup.createFromStrings("a.txt");
			TreeFilter filterB = PathFilterGroup.createFromStrings("b");
			TreeFilter filterB2 = PathFilterGroup.createFromStrings("b/2.txt");

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());

			List<DiffEntry> result = DiffEntry.scan(walk
					new TreeFilter[] { filterA
			assertEquals(1

			walk.reset(c1.getTree()
			result = DiffEntry.scan(walk
					new TreeFilter[] { filterA
			assertEquals(4

			walk.reset(c1.getTree()
			result = DiffEntry.scan(walk
					new TreeFilter[] { filterA
			assertEquals(5
		}
	}

