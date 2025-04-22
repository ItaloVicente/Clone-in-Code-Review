		try {
			git.cherryPick().include(commitM).setMainlineParentNumber(3).call();
			fail("specifying a non-existent parent should fail");
		} catch (JGitInternalException e) {
			assertTrue(e.getMessage().endsWith(
					"does not have a parent number 3."));
		}

		CherryPickResult result = git.cherryPick().include(commitM)
				.setMainlineParentNumber(1).call();
		assertEquals(CherryPickStatus.OK, result.getStatus());
		checkFile(new File(db.getWorkTree(), "file"), "1\n2\n3\n4\n5\n");

		git.reset().setMode(ResetType.HARD).setRef(commitT.getName()).call();

		CherryPickResult result2 = git.cherryPick().include(commitM)
				.setMainlineParentNumber(2).call();
		assertEquals(CherryPickStatus.OK, result2.getStatus());
		checkFile(new File(db.getWorkTree(), "file"), "a\n2\n3\n");
