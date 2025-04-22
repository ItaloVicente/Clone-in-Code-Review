	private String stripGitCloneCommand(String input) {
		if (input.startsWith(GIT_CLONE_COMMAND_PREFIX)) {
			return input.substring(GIT_CLONE_COMMAND_PREFIX.length()).trim();
		}
		return input.trim();
	}

