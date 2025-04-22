		assertNotNull("Expected '" + branchName
				+ "' to resolve to non-null ObjectId on remote repository",
				pushed);
		ObjectId local = repository.resolve(branchName);
		assertEquals(
				"Expected local branch to be the same as branch on remote after pushing",
				local, pushed);
