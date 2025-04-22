		Git git = new Git(db);
		git.add().addFilepattern("a.txt").call();
		RevCommit commit = git.commit().setMessage("initial commit").call();
		TreeWalk tw = TreeWalk.forPath(db, "a.txt", commit.getTree());
		assertEquals("6b584e8ece562ebffc15d38808cd6b98fc3d97ea",
				tw.getObjectId(0).getName());

		writer = new PrintWriter(file);
		writer.print("content2");
		writer.close();
		commit = git.commit().setMessage("second commit").call();
		tw = TreeWalk.forPath(db, "a.txt", commit.getTree());
		assertEquals("6b584e8ece562ebffc15d38808cd6b98fc3d97ea",
				tw.getObjectId(0).getName());

		commit = git.commit().setAll(true).setMessage("third commit")
				.setAll(true).call();
		tw = TreeWalk.forPath(db, "a.txt", commit.getTree());
		assertEquals("db00fd65b218578127ea51f3dffac701f12f486a",
				tw.getObjectId(0).getName());
