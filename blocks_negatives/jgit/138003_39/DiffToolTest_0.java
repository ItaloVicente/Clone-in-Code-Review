		@Argument(index = 1, metaVar = "metaVar_arg")
		List<String> arguments = new ArrayList<>();
	}

	private String[] runAndCaptureUsingInitRaw(String... args)
			throws Exception {
		CLIGitCommand.Result result = new CLIGitCommand.Result();

		GitCliJGitWrapperParser bean = new GitCliJGitWrapperParser();
		CmdLineParser clp = new CmdLineParser(bean);
		clp.parseArgument(args);

		TextBuiltin cmd = bean.subcommand;
		cmd.initRaw(db, null, null, result.out, result.err);
		cmd.execute(bean.arguments.toArray(new String[bean.arguments.size()]));
		if (cmd.getOutputWriter() != null) {
			cmd.getOutputWriter().flush();
		}
		if (cmd.getErrorWriter() != null) {
			cmd.getErrorWriter().flush();
		}
		return result.outLines().toArray(new String[0]);
	}

	private static final String TOOL_NAME = "some_tool";
	private Git git;
