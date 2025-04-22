		try (Git git = Git.open(db.getDirectory()
			git.add().addFilepattern(path).addFilepattern(path2).call();
			RevCommit commit1 = git.commit().setMessage("commit").call();
			try (TreeWalk walk = new TreeWalk(db)) {
				walk.addTree(commit1.getTree());
				walk.next();
				assertEquals(path2
				assertEquals(FileMode.EXECUTABLE_FILE
				walk.next();
				assertEquals(path
				assertEquals(FileMode.REGULAR_FILE
			}
