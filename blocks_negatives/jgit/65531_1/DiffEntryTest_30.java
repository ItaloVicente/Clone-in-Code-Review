		Git git = new Git(db);
		File tree = new File(new File(db.getWorkTree(), "a"), "b");
		FileUtils.mkdirs(tree);
		File file = new File(tree, "c.txt");
		FileUtils.createNewFile(file);
		write(file, "content");
		git.add().addFilepattern("a").call();
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		write(file, "new line");
		RevCommit c2 = git.commit().setAll(true).setMessage("second commit")
				.call();

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c1.getTree());
		walk.addTree(c2.getTree());
		List<DiffEntry> result = DiffEntry.scan(walk, true);

		assertThat(result, notNullValue());
		assertThat(Integer.valueOf(result.size()), is(Integer.valueOf(3)));

		DiffEntry entry = result.get(0);
		assertThat(entry.getChangeType(), is(ChangeType.MODIFY));
		assertThat(entry.getNewPath(), is("a"));

		entry = result.get(1);
		assertThat(entry.getChangeType(), is(ChangeType.MODIFY));
		assertThat(entry.getNewPath(), is("a/b"));

		entry = result.get(2);
		assertThat(entry.getChangeType(), is(ChangeType.MODIFY));
		assertThat(entry.getNewPath(), is("a/b/c.txt"));
