
		try {
			git.reset().setRef(Constants.HEAD).call();
			fail("Expected JGitInternalException didn't occur");
		} catch (JGitInternalException e) {
		}
