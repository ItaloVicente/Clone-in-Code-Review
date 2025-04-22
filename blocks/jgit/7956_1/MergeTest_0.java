	@Test
	public void testSquashSelf() throws Exception {
		assertEquals(" (nothing to squash)Already up-to-date."
				execute("git merge master --squash")[0]);
	}

