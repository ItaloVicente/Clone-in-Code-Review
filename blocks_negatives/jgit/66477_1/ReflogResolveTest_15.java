		Git git = new Git(db);
		writeTrashFile("file.txt", "content");
		git.add().addFilepattern("file.txt").call();
		RevCommit c1 = git.commit().setMessage("create file").call();
		writeTrashFile("file.txt", "content2");
		git.add().addFilepattern("file.txt").call();
		RevCommit c2 = git.commit().setMessage("edit file").call();

		assertEquals(c2, db.resolve("master@{0}"));
		assertEquals(c1, db.resolve("master@{1}"));
