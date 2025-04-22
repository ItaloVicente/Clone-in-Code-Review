		Git git = new Git(db);
		File file = writeTrashFile("a.txt", "content");
		git.add().addFilepattern("a.txt").call();
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		delete(file);
		RevCommit c2 = git.commit().setAll(true).setMessage("delete a.txt")
				.call();

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c1.getTree());
		walk.addTree(c2.getTree());
		List<DiffEntry> result = DiffEntry.scan(walk);

		assertThat(result, notNullValue());
		assertThat(Integer.valueOf(result.size()), is(Integer.valueOf(1)));

		DiffEntry entry = result.get(0);
		assertThat(entry.getOldPath(), is("a.txt"));
		assertThat(entry.getNewPath(), is(DEV_NULL));
		assertThat(entry.getChangeType(), is(ChangeType.DELETE));
