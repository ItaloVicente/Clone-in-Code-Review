	@Test
	public void testCheckoutUnresolvedHead() throws Exception {
		assertEquals(
				"error: pathspec 'HEAD' did not match any file(s) known to git."
				execute("git checkout HEAD"));
	}

	@Test
	public void testCheckoutHead() throws Exception {
		new Git(db).commit().setMessage("initial commit").call();

		assertEquals(""
	}

