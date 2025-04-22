		Git git = new Git(db);

		writeTrashFile("a", "first line\nsec. line\nthird line\n");
		git.add().addFilepattern("a").call();
		RevCommit firstCommit = git.commit().setMessage("create a").call();

		writeTrashFile("b", "content\n");
		git.add().addFilepattern("b").call();
		git.commit().setMessage("create b").call();

		writeTrashFile("a", "first line\nsec. line\nthird line\nfourth line\n");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("enlarged a").call();

		writeTrashFile("a",
				"first line\nsecond line\nthird line\nfourth line\n");
		git.add().addFilepattern("a").call();
		RevCommit fixingA = git.commit().setMessage("fixed a").call();

		git.branchCreate().setName("side").setStartPoint(firstCommit).call();
		checkoutBranch("refs/heads/side");

		writeTrashFile("a", "first line\nsec. line\nthird line\nfeature++\n");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("enhanced a").call();

		CherryPickResult pickResult = git.cherryPick().include(fixingA)
				.setNoCommit(noCommit).call();

		assertEquals(CherryPickStatus.OK, pickResult.getStatus());
		assertFalse(new File(db.getWorkTree(), "b").exists());
		checkFile(new File(db.getWorkTree(), "a"),
				"first line\nsecond line\nthird line\nfeature++\n");
		Iterator<RevCommit> history = git.log().call().iterator();
		if (!noCommit)
			assertEquals("fixed a", history.next().getFullMessage());
		assertEquals("enhanced a", history.next().getFullMessage());
		assertEquals("create a", history.next().getFullMessage());
		assertFalse(history.hasNext());
