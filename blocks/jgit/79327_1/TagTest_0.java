
	@Test
	public void testTagDelete() throws Exception {
		git.tag().setName("test").call();

		Ref ref = git.getRepository().getTags().get("test");
		assertEquals("refs/tags/test"

		assertEquals(""
		Ref deletedRef = git.getRepository().getTags().get("test");
		assertEquals(null
	}
