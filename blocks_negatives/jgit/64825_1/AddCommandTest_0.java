		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter", "tstFilter", "clean",
				"sh " + slashify(script.getPath()));
		config.save();
		git.add().addFilepattern("src/a.txt").call();

		String gitDir = db.getDirectory().getAbsolutePath();
		assertEquals("[src/a.txt, mode:100644, content:" + gitDir
				+ "\n]", indexState(CONTENT));
		assertTrue(new File(db.getWorkTree(), "xyz").exists());
