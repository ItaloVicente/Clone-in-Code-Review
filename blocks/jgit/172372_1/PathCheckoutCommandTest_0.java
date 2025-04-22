	@Test
	public void testCheckoutFileWithConflict() throws Exception {
		setupConflictingState();
		assertEquals('[' + FILE1 + ']'
				git.status().call().getConflicting().toString());
		git.checkout().setStartPoint(Constants.HEAD).addPath(FILE1).call();
		assertEquals("3"
		assertTrue(git.status().call().isClean());
	}

