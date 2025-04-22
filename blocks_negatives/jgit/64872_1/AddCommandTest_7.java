		Git git = new Git(db);
		db.getConfig().setString("core", null, "autocrlf", "false");
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt, mode:100644, content:row1\r\nrow2]",
				indexState(CONTENT));
		db.getConfig().setString("core", null, "autocrlf", "true");
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt, mode:100644, content:row1\nrow2]",
				indexState(CONTENT));
		db.getConfig().setString("core", null, "autocrlf", "input");
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt, mode:100644, content:row1\nrow2]",
				indexState(CONTENT));
