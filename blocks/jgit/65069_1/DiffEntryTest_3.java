		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			File file = writeTrashFile("a.txt"
			git.add().addFilepattern("a.txt").call();
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			delete(file);
			RevCommit c2 = git.commit().setAll(true).setMessage("delete a.txt")
					.call();

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			List<DiffEntry> result = DiffEntry.scan(walk);

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getOldPath()
			assertThat(entry.getNewPath()
			assertThat(entry.getChangeType()
		}
