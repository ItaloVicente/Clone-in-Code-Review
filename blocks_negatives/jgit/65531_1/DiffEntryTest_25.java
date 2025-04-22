		Git git = new Git(db);
		git.add().addFilepattern("a.txt").call();
		RevCommit c = git.commit().setMessage("initial commit").call();

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(new EmptyTreeIterator());
		walk.addTree(c.getTree());
		List<DiffEntry> result = DiffEntry.scan(walk);

		assertThat(result, notNullValue());
		assertThat(Integer.valueOf(result.size()), is(Integer.valueOf(1)));

		DiffEntry entry = result.get(0);
		assertThat(entry.getChangeType(), is(ChangeType.ADD));
		assertThat(entry.getNewPath(), is("a.txt"));
		assertThat(entry.getOldPath(), is(DEV_NULL));
