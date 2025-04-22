		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			File tree = new File(new File(db.getWorkTree()
			FileUtils.mkdirs(tree);
			File file = new File(tree
			FileUtils.createNewFile(file);
			write(file
			git.add().addFilepattern("a").call();
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			write(file
			RevCommit c2 = git.commit().setAll(true).setMessage("second commit")
					.call();

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			walk.setRecursive(true);
			List<DiffEntry> result = DiffEntry.scan(walk);

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
		}
