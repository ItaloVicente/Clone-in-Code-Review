		git.add().addFilepattern(path).addFilepattern(path2).call();
		RevCommit commit1 = git.commit().setMessage("commit").call();
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(commit1.getTree());
			walk.next();
			assertEquals(path2, walk.getPathString());
			assertEquals(FileMode.EXECUTABLE_FILE, walk.getFileMode(0));
			walk.next();
			assertEquals(path, walk.getPathString());
			assertEquals(FileMode.REGULAR_FILE, walk.getFileMode(0));
