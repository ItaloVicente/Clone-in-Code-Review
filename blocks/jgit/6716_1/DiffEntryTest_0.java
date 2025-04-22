	@Test
	public void shouldMarkEntriesWhenGivenMarkTreeFilter() throws Exception {
		Git git = new Git(db);
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		writeTrashFile("a.txt"
		writeTrashFile("b.txt"
		writeTrashFile("c.txt"
		git.add().addFilepattern("a.txt").addFilepattern("b.txt")
				.addFilepattern("c.txt").call();
		RevCommit c2 = git.commit().setMessage("second commit").call();
		TreeFilter markTreeFilter = PathFilterGroup.createFromStrings("a.txt"
				"b.txt");

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c1.getTree());
		walk.addTree(c2.getTree());
		List<DiffEntry> result = DiffEntry.scan(walk

		assertThat(result
		assertThat(Integer.valueOf(result.size())

		DiffEntry entryA = result.get(0);
		DiffEntry entryB = result.get(1);
		DiffEntry entryC = result.get(2);

		assertThat(entryA.getNewPath()
		assertTrue(entryA.isMarked());
		assertThat(entryB.getNewPath()
		assertTrue(entryB.isMarked());
		assertThat(entryC.getNewPath()
		assertFalse(entryC.isMarked());
	}

