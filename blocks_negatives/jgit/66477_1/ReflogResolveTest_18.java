		Git git = new Git(db);
		writeTrashFile("file.txt", "content");
		git.add().addFilepattern("file.txt").call();
		RevCommit c1 = git.commit().setMessage("create file").call();
		writeTrashFile("file.txt", "content2");
		git.add().addFilepattern("file.txt").call();
		RevCommit c2 = git.commit().setMessage("edit file").call();

		git.checkout().setCreateBranch(true).setName("newbranch")
				.setStartPoint(c1).call();

		git.checkout().setName(c1.getName()).call();

		git.checkout().setName("master").call();

		assertEquals(c1.getName(), db.simplify("@{-1}"));
		assertEquals("newbranch", db.simplify("@{-2}"));
		assertEquals("master", db.simplify("@{-3}"));

		try {
			db.resolve("@{-1}@{0}");
			fail();
		} catch (RevisionSyntaxException e) {
