		Git git = new Git(db);
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		FileUtils.mkdir(new File(db.getWorkTree(), "b"));
		writeTrashFile("a.txt", "a");
		writeTrashFile("b/1.txt", "b1");
		writeTrashFile("b/2.txt", "b2");
		writeTrashFile("c.txt", "c");
		git.add().addFilepattern("a.txt").addFilepattern("b")
				.addFilepattern("c.txt").call();
		RevCommit c2 = git.commit().setMessage("second commit").call();
		TreeFilter filterA = PathFilterGroup.createFromStrings("a.txt");
		TreeFilter filterB = PathFilterGroup.createFromStrings("b");
		TreeFilter filterB2 = PathFilterGroup.createFromStrings("b/2.txt");

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c1.getTree());
		walk.addTree(c2.getTree());
		List<DiffEntry> result = DiffEntry.scan(walk, true, new TreeFilter[] {
				filterA, filterB, filterB2 });

		assertThat(result, notNullValue());
		assertEquals(5, result.size());

		DiffEntry entryA = result.get(0);
		DiffEntry entryB = result.get(1);
		DiffEntry entryB1 = result.get(2);
		DiffEntry entryB2 = result.get(3);
		DiffEntry entryC = result.get(4);

		assertThat(entryA.getNewPath(), is("a.txt"));
		assertTrue(entryA.isMarked(0));
		assertFalse(entryA.isMarked(1));
		assertFalse(entryA.isMarked(2));
		assertEquals(1, entryA.getTreeFilterMarks());

		assertThat(entryB.getNewPath(), is("b"));
		assertFalse(entryB.isMarked(0));
		assertTrue(entryB.isMarked(1));
		assertTrue(entryB.isMarked(2));
		assertEquals(6, entryB.getTreeFilterMarks());

		assertThat(entryB1.getNewPath(), is("b/1.txt"));
		assertFalse(entryB1.isMarked(0));
		assertTrue(entryB1.isMarked(1));
		assertFalse(entryB1.isMarked(2));
		assertEquals(2, entryB1.getTreeFilterMarks());

		assertThat(entryB2.getNewPath(), is("b/2.txt"));
		assertFalse(entryB2.isMarked(0));
		assertTrue(entryB2.isMarked(1));
		assertTrue(entryB2.isMarked(2));
		assertEquals(6, entryB2.getTreeFilterMarks());

		assertThat(entryC.getNewPath(), is("c.txt"));
		assertFalse(entryC.isMarked(0));
		assertFalse(entryC.isMarked(1));
		assertFalse(entryC.isMarked(2));
		assertEquals(0, entryC.getTreeFilterMarks());
