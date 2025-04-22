	@Test
	public void testDiffMaxCount() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db);
				DiffFormatter dfmt = new DiffFormatter(
						NullOutputStream.INSTANCE)) {
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			FileUtils.mkdir(new File(db.getWorkTree()
			writeTrashFile("a.txt"
			writeTrashFile("b/1.txt"
			writeTrashFile("b/2.txt"
			writeTrashFile("c.txt"
			git.add().addFilepattern("a.txt").addFilepattern("b")
					.addFilepattern("c.txt").call();
			RevCommit c2 = git.commit().setMessage("second commit").call();

			dfmt.setReader(walk.getObjectReader()
			dfmt.setDetectRenames(false);

			List<DiffEntry> diffs;

			dfmt.setMaxDiffEntryScan(1);
			diffs = dfmt.scan(c1.getTree()
			assertEquals(1

			dfmt.setMaxDiffEntryScan(2);
			diffs = dfmt.scan(c1.getTree()
			assertEquals(2

			dfmt.setMaxDiffEntryScan(-1);
			diffs = dfmt.scan(c1.getTree()
			assertEquals(4
		}
	}

