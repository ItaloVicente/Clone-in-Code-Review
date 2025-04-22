		Git git = new Git(db);
		writeTrashFile("a", "content of a");
		writeTrashFile("D/b", "content of b");
		writeTrashFile("D/c", "content of c");
		writeTrashFile("D/D/d", "content of d");
		git.add().addFilepattern(".").call();

		writeTrashFile("a", "new content of a");
		writeTrashFile("D/b", "new content of b");
		writeTrashFile("D/D/d", "new content of d");


		Status stat = git.status().addPath("x").call();
		assertEquals(0, stat.getModified().size());

		stat = git.status().addPath("a").call();
		assertEquals(Sets.of("a"), stat.getModified());

		stat = git.status().addPath("D").call();
		assertEquals(Sets.of("D/b", "D/D/d"), stat.getModified());

		stat = git.status().addPath("D/D").addPath("a").call();
		assertEquals(Sets.of("a", "D/D/d"), stat.getModified());

		stat = git.status().call();
		assertEquals(Sets.of("a", "D/b", "D/D/d"), stat.getModified());
