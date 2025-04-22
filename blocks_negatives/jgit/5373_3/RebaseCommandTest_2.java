		JGitInternalException exception = null;
		try {
			git.rebase().setUpstream("refs/heads/master").call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Checkout conflict with files: \nfile1",
				exception.getMessage());
