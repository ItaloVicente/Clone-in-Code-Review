
		RevCommit developHead = gfRepo.findHead();
		assertNotEquals(developHead, taggedCommit);

		RevCommit masterHead = gfRepo.findHead(MY_MASTER);
		assertEquals(masterHead, taggedCommit);
