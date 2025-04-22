
		try {
			git.rebase().setUpstream("refs/heads/master").call();
			fail("Expected exception was not thrown");
		} catch (WrongRepositoryStateException e) {
		}

