	protected void assertNoCommitsMarkedStart() {
		if (roots.isEmpty())
			return;
		throw new IllegalStateException(
				JGitText.get().commitsHaveAlreadyBeenMarkedAsStart);
	}

