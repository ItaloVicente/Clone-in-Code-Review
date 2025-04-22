		Git git = new Git(db);
		git.add().addFilepattern("a.txt").call();
		RevCommit c = git.commit().setMessage("initial commit").call();
		writeTrashFile("b.txt", "new line");

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c.getTree());
		walk.addTree(new FileTreeIterator(db));
		List<DiffEntry> result = DiffEntry.scan(walk, true);

		assertThat(Integer.valueOf(result.size()), is(Integer.valueOf(1)));
		DiffEntry entry = result.get(0);

		assertThat(entry.getChangeType(), is(ChangeType.ADD));
		assertThat(entry.getNewPath(), is("b.txt"));
