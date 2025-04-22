		Git git = new Git(db);
		writeTrashFile("a", "start");
		git.add().addFilepattern("a").call();
		RevCommit base = git.commit().setMessage("init").call();
		FileUtils.createSymLink(new File(db.getWorkTree(), "link"), "target");
		FileUtils.mkdir(new File(db.getWorkTree(), "target"));
		writeTrashFile("target/file", "someData");
		git.add().addFilepattern("target").addFilepattern("link").call();
		git.commit().setMessage("add target").call();
		assertEquals(4, db.getWorkTree().list().length); // self-check
		git.checkout().setName(base.name()).call();
		assertEquals(2, db.getWorkTree().list().length); // self-check
		git.checkout().setName("master").call();
		assertEquals(4, db.getWorkTree().list().length);
		String data = read(new File(db.getWorkTree(), "target/file"));
		assertEquals(8, new File(db.getWorkTree(), "target/file").length());
		assertEquals("someData", data);
		data = read(new File(db.getWorkTree(), "link/file"));
		assertEquals("target",
				FileUtils.readSymLink(new File(db.getWorkTree(), "link")));
		assertEquals("someData", data);
