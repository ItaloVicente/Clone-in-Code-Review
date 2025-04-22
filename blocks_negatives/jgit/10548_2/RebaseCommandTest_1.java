		try {
			RebaseResult rebase = git.rebase().setUpstream("refs/heads/master")
					.call();
			fail("MultipleParentsNotAllowedException expected: "
					+ rebase.getStatus());
		} catch (JGitInternalException e) {
		}
