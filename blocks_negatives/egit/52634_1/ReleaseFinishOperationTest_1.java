
		RevCommit developHead = gfRepo.findHead();
		assertEquals(branchCommit, developHead);

		RevCommit masterHead = gfRepo.findHead(MY_MASTER);
		assertEquals(branchCommit, masterHead);
