
		newBranch = createBranch(git
				null);
		assertEquals("refs/heads/newBranch"
		assertEquals(3
		assertEquals(0
				.size()
				- remoteBefore);
		assertEquals(3
				.size()
				- allBefore);

		newBranch = createBranch(git
		assertEquals("refs/heads/newBranch2"
		assertEquals(4
		assertEquals(0
				.size()
				- remoteBefore);
		assertEquals(4
				.size()
				- allBefore);
