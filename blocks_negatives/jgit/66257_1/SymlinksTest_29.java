		Git git = new Git(db);
		writeTrashFile("a", "start");
		git.add().addFilepattern("a").call();
		RevCommit base = git.commit().setMessage("init").call();
		writeTrashFile("target", "someData");
		FileUtils.createSymLink(new File(db.getWorkTree(), "link"), "target");
		git.add().addFilepattern("target").addFilepattern("link").call();
		git.commit().setMessage("add target").call();
		assertEquals(4, db.getWorkTree().list().length); // self-check
		git.checkout().setName(base.name()).call();
		assertEquals(2, db.getWorkTree().list().length); // self-check
		git.checkout().setName("master").call();
		assertEquals(4, db.getWorkTree().list().length);
		String data = read(new File(db.getWorkTree(), "target"));
		assertEquals(8, new File(db.getWorkTree(), "target").length());
		assertEquals("someData", data);
		data = read(new File(db.getWorkTree(), "link"));
		assertEquals("target",
				FileUtils.readSymLink(new File(db.getWorkTree(), "link")));
		assertEquals("someData", data);
