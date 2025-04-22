	@Test
	public void shouldNotListMoreEntriesAsRequested() throws Exception {
		Git git = new Git(db);
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		for (int i = 1; i <= 8; i++) {
			writeTrashFile(i + ".txt"
			git.add().addFilepattern(i + ".txt").call();
		}
		RevCommit c2 = git.commit().setMessage("second commit").call();

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c1.getTree());
		walk.addTree(c2.getTree());
		List<DiffEntry> result = DiffEntry.scan(walk

		assertThat(result
		assertThat(Integer.valueOf(result.size())
	}

