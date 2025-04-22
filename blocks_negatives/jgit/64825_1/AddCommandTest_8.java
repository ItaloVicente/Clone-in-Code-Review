		Git git = new Git(db);
		db.getConfig().setString("core", null, "autocrlf", "false");
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt, mode:100644, content:" + data + "]",
				indexState(CONTENT));
		db.getConfig().setString("core", null, "autocrlf", "true");
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt, mode:100644, content:" + lfData + "]",
				indexState(CONTENT));
		db.getConfig().setString("core", null, "autocrlf", "input");
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt, mode:100644, content:" + lfData + "]",
				indexState(CONTENT));
