		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("edit file").call();

			assertEquals(c2
			assertEquals(c1
		}
