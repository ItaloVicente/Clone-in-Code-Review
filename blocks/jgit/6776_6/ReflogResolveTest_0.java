		RevCommit c1 = git.commit().setMessage("create file").call();
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c2 = git.commit().setMessage("edit file").call();

		git.checkout().setCreateBranch(true).setName("newbranch")
				.setStartPoint(c1).call();

		git.checkout().setName(c1.getName()).call();

		git.checkout().setName("master").call();

		assertEquals(c1.getName()
		assertEquals("newbranch"
		assertEquals("master"

