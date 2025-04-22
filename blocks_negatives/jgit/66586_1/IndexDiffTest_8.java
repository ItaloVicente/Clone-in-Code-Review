		Git git = new Git(db);
		FileBasedConfig config = db.getConfig();

		config.setEnum(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOCRLF, AutoCRLF.FALSE);
		config.save();

		writeTrashFile("crlf.txt", "this\r\ncontains\r\ncrlf\r\n");
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("Add crlf.txt").call();

		config.setEnum(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOCRLF, AutoCRLF.INPUT);
		config.save();

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db, Constants.HEAD, iterator);
		diff.diff();

		assertTrue(
				"Expected no modified files, but there were: "
						+ diff.getModified(), diff.getModified().isEmpty());
