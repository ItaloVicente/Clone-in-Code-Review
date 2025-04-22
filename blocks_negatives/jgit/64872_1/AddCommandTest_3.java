		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter", "tstFilter", "clean",
				"sh " + script.getPath());
		config.save();
		writeTrashFile(".gitattributes", "*.txt filter=tstFilter");

		try {
			git.add().addFilepattern("a.txt").call();
			fail("Didn't received the expected exception");
		} catch (FilterFailedException e) {
			assertEquals(127, e.getReturnCode());
