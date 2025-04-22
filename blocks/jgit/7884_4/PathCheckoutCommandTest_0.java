		setupConflictingState();

		git.checkout().addPath(FILE1).call();
	}

	@Test
	public void testCheckoutOurs() throws Exception {
		setupConflictingState();

		git.checkout().setStage(Stage.OURS).addPath(FILE1).call();

		assertEquals("3"
		assertStageOneToThree(FILE1);
	}

	@Test
	public void testCheckoutTheirs() throws Exception {
		setupConflictingState();

		git.checkout().setStage(Stage.THEIRS).addPath(FILE1).call();

		assertEquals("Conflicting"
		assertStageOneToThree(FILE1);
	}

	@Test(expected = IllegalStateException.class)
	public void testStageNotPossibleWithBranch() throws Exception {
		git.checkout().setStage(Stage.OURS).setStartPoint("master").call();
	}

	private void setupConflictingState() throws Exception {
