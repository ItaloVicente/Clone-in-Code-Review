
	@Test
	public void testDetachedHeadOnCheckout() throws JGitInternalException
			RefAlreadyExistsException
			InvalidRefNameException
		CheckoutCommand co = git.checkout();
		co.setName("master").call();

		String commitId = db.getRef(Constants.MASTER).getObjectId().name();
		co = git.checkout();
		co.setName(commitId).call();

		Ref head = db.getRef(Constants.HEAD);
		assertFalse(head.isSymbolic());
		assertSame(head
	}
