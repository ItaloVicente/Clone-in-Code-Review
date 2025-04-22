		Git git = new Git(db);
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		writeTrashFile("a.txt", "content");
		git.add().addFilepattern("a.txt").call();
		RevCommit c2 = git.commit().setMessage("second commit").call();

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c1.getTree());
		walk.addTree(c2.getTree());
		List<DiffEntry> result = DiffEntry.scan(walk);

		assertThat(result, notNullValue());
		assertThat(Integer.valueOf(result.size()), is(Integer.valueOf(1)));

		DiffEntry entry = result.get(0);
		assertThat(entry.getChangeType(), is(ChangeType.ADD));
		assertThat(entry.getNewPath(), is("a.txt"));
		assertThat(entry.getOldPath(), is(DEV_NULL));
