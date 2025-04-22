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
		try {
			cmd.run(str);
			return getOutput(cmd.result);
		} catch (Throwable e) {
			return cmd.result.errLines();
		}
	}

	private static List<String> getOutput(Result result) {
		if (result.ex instanceof TerminatedByHelpException) {
			return result.errLines();
		}
		return result.outLines();
	}

	private void run(String commandLine) throws Exception {
		String[] argv = convertToMainArgs(commandLine);
