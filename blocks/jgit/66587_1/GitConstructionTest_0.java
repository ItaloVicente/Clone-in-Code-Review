		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			writeTrashFile("Test.txt"
			git.add().addFilepattern("Test.txt").call();
			git.commit().setMessage("Initial commit").call();
		}
