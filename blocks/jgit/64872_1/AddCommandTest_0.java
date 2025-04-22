		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.save();
			git.add().addFilepattern("src/a.txt").call();

			String gitDir = db.getDirectory().getAbsolutePath();
			assertEquals("[src/a.txt
					+ "\n]"
			assertTrue(new File(db.getWorkTree()
		}
