		setupConflictingState();

		git.checkout().addPath(FILE1).call();
	}

	@Test
	public void testCheckoutOurs() throws Exception {
		setupConflictingState();

		git.checkout().setOurs(true).addPath(FILE1).call();

		assertEquals("3"
		assertStageOneToThree(FILE1);
	}

	@Test
	public void testCheckoutTheirs() throws Exception {
		setupConflictingState();

		git.checkout().setTheirs(true).addPath(FILE1).call();

		assertEquals("Conflicting"
		assertStageOneToThree(FILE1);
	}

	@Test(expected = IllegalStateException.class)
	public void testOursNotPossibleWithBranch() throws Exception {
		git.checkout().setOurs(true).setStartPoint("master").call();
	}

	@Test(expected = IllegalStateException.class)
	public void testTheirsNotPossibleWithBranch() throws Exception {
		git.checkout().setTheirs(true).setStartPoint("master").call();
	}

	@Test(expected = IllegalStateException.class)
	public void testOursNotPossibleWithTheirs() throws Exception {
		git.checkout().setOurs(true).setTheirs(true).call();
	}

	private void setupConflictingState() throws Exception {
