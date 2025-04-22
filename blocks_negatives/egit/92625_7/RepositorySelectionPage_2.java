	private String stripGitCloneCommand(String input) {
		input = input.trim();
		if (input.startsWith(GIT_CLONE_COMMAND_PREFIX)) {
			return input.substring(GIT_CLONE_COMMAND_PREFIX.length()).trim();
		}
		return input;
	}

