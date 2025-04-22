		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter", "tstFilter", "clean",
				"sh " + slashify(script.getPath()));
		config.save();
		writeTrashFile(".gitattributes", "*.txt filter=tstFilter");

		try {
			git.add().addFilepattern("a.txt").call();
			fail("Didn't received the expected exception");
		} catch (FilterFailedException e) {
			assertEquals(12, e.getReturnCode());
