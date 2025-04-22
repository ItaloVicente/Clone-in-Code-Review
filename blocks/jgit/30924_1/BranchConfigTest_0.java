	@Test
	public void isRebase() {
				+ "[branch \"undefined\"]\n"
				+ "[branch \"false\"]\n"
				+ "  rebase = false\n"
				+ "[branch \"true\"]\n"
				+ "  rebase = true\n");

		assertFalse(new BranchConfig(c
		assertFalse(new BranchConfig(c
		assertTrue(new BranchConfig(c
	}

