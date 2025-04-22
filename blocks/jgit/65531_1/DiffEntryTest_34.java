		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
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
					filterA

			assertThat(result
			assertEquals(5

			DiffEntry entryA = result.get(0);
			DiffEntry entryB = result.get(1);
			DiffEntry entryB1 = result.get(2);
			DiffEntry entryB2 = result.get(3);
			DiffEntry entryC = result.get(4);

			assertThat(entryA.getNewPath()
			assertTrue(entryA.isMarked(0));
			assertFalse(entryA.isMarked(1));
			assertFalse(entryA.isMarked(2));
			assertEquals(1

			assertThat(entryB.getNewPath()
			assertFalse(entryB.isMarked(0));
			assertTrue(entryB.isMarked(1));
			assertTrue(entryB.isMarked(2));
			assertEquals(6

			assertThat(entryB1.getNewPath()
			assertFalse(entryB1.isMarked(0));
			assertTrue(entryB1.isMarked(1));
			assertFalse(entryB1.isMarked(2));
			assertEquals(2

			assertThat(entryB2.getNewPath()
			assertFalse(entryB2.isMarked(0));
			assertTrue(entryB2.isMarked(1));
			assertTrue(entryB2.isMarked(2));
			assertEquals(6

			assertThat(entryC.getNewPath()
			assertFalse(entryC.isMarked(0));
			assertFalse(entryC.isMarked(1));
			assertFalse(entryC.isMarked(2));
			assertEquals(0
		}
