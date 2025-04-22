		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			writeTrashFile("a.txt"
			git.add().addFilepattern("a.txt").call();
			RevCommit c2 = git.commit().setMessage("second commit").call();

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			List<DiffEntry> result = DiffEntry.scan(walk);

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
			assertThat(entry.getOldPath()
		}
