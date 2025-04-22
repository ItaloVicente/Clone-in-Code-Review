		git2.add().addFilepattern(path).addFilepattern(path2).call();
		RevCommit commit2 = git2.commit().setMessage("commit2").call();
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(commit2.getTree());
			walk.next();
			assertEquals(path2, walk.getPathString());
			assertEquals(FileMode.EXECUTABLE_FILE, walk.getFileMode(0));
			walk.next();
			assertEquals(path, walk.getPathString());
			assertEquals(FileMode.REGULAR_FILE, walk.getFileMode(0));
