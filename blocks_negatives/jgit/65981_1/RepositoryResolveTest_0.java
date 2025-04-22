		Git git = new Git(db);
		writeTrashFile("file.txt", "content");
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();
		assertEquals("master", db.simplify("master"));
		assertEquals("refs/heads/master", db.simplify("refs/heads/master"));
		assertEquals("HEAD", db.simplify("HEAD"));
