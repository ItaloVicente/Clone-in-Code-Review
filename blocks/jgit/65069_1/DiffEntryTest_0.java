		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			git.add().addFilepattern("a.txt").call();
			RevCommit c = git.commit().setMessage("initial commit").call();

			walk.addTree(new EmptyTreeIterator());
			walk.addTree(c.getTree());
			List<DiffEntry> result = DiffEntry.scan(walk);

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
			assertThat(entry.getOldPath()
		}
