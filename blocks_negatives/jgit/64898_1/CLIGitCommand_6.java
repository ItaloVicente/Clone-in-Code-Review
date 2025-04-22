		CLIGitCommand bean = new CLIGitCommand();
		final CmdLineParser clp = new CmdLineParser(bean);
		clp.parseArgument(argv);

		final TextBuiltin cmd = bean.getSubcommand();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		cmd.outs = baos;
		if (cmd.requiresRepository())
			cmd.init(db, null);
		else
			cmd.init(null, null);
		try {
			cmd.execute(bean.getArguments().toArray(
					new String[bean.getArguments().size()]));
		} finally {
			if (cmd.outw != null)
				cmd.outw.flush();
