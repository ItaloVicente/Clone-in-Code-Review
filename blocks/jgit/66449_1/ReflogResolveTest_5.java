		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("create file").call();
			try {
				db.resolve("master@{yesterday}");
				fail("Exception not thrown");
			} catch (RevisionSyntaxException e) {
				assertNotNull(e);
			}
