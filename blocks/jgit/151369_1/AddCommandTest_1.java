		try (Git git2 = Git.open(db.getDirectory()
			git2.add().addFilepattern(path).addFilepattern(path2).call();
			RevCommit commit2 = git2.commit().setMessage("commit2").call();
			try (TreeWalk walk = new TreeWalk(db)) {
				walk.addTree(commit2.getTree());
				walk.next();
				assertEquals(path2
				assertEquals(FileMode.EXECUTABLE_FILE
				walk.next();
				assertEquals(path
				assertEquals(FileMode.REGULAR_FILE
			}
