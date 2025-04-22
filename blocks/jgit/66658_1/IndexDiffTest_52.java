		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();

			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			config.save();

			writeTrashFile("crlf.txt"
			git.add().addFilepattern("crlf.txt").call();
			git.commit().setMessage("Add crlf.txt").call();

			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			config.save();

			FileTreeIterator iterator = new FileTreeIterator(db);
			IndexDiff diff = new IndexDiff(db
			diff.diff();

			assertTrue(
					"Expected no modified files
							+ diff.getModified()
		}
