		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + script.getPath());
			config.save();
			writeTrashFile(".gitattributes"

			try {
				git.add().addFilepattern("a.txt").call();
				fail("Didn't received the expected exception");
			} catch (FilterFailedException e) {
				assertEquals(127
			}
