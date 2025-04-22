	@Test
	public void testCheckoutLightweightTag() throws Exception {
		git.tag().setAnnotated(false).setName("test-tag")
				.setObjectId(initialCommit).call();
		Ref result = git.checkout().setName("test-tag").call();

		assertNull(result);
		assertEquals(initialCommit.getId()
		assertHeadDetached();
	}

	@Test
	public void testCheckoutAnnotatedTag() throws Exception {
		git.tag().setAnnotated(true).setName("test-tag")
				.setObjectId(initialCommit).call();
		Ref result = git.checkout().setName("test-tag").call();

		assertNull(result);
		assertEquals(initialCommit.getId()
		assertHeadDetached();
	}

