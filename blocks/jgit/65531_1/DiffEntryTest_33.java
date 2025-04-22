		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			git.add().addFilepattern("a.txt").call();
			RevCommit c = git.commit().setMessage("initial commit").call();
			writeTrashFile("b.txt"

			walk.addTree(c.getTree());
			walk.addTree(new FileTreeIterator(db));
			List<DiffEntry> result = DiffEntry.scan(walk

			assertThat(Integer.valueOf(result.size())
			DiffEntry entry = result.get(0);

			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
		}
