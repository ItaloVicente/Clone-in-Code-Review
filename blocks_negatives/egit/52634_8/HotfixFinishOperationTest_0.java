
		RevCommit developHead = gfRepo.findHead(DEVELOP);
		assertEquals(branchCommit, developHead);

		RevCommit masterHead = gfRepo.findHead(MY_MASTER);
		assertEquals(branchCommit, masterHead);
