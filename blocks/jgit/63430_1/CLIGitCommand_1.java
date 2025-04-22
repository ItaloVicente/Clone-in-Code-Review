		Result result = executeRaw(str
		return getOutput(result);
	}

	public static Result executeRaw(String str
			throws Exception {
		CLIGitCommand cmd = new CLIGitCommand(db);
		cmd.run(str);
		return cmd.result;
	}

	public static List<String> executeUnchecked(String str
			throws Exception {
		CLIGitCommand cmd = new CLIGitCommand(db);
