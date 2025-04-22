
	@Test
	public void testTagDelete() throws Exception {
		git.tag().setName("test").call();

		Ref ref = git.getRepository().getTags().get("test");
		assertEquals("refs/tags/test"

		assertEquals(""
		Ref deletedRef = git.getRepository().getTags().get("test");
		assertEquals(null
	}

	@Test
	public void testTagDeleteFail() throws Exception {
		try {
			assertEquals("fatal: error: tag 'test' not found."
					executeUnchecked("git tag -d test")[0]);
		} catch (Die e) {
			assertEquals("fatal: error: tag 'test' not found"
		}
	}
