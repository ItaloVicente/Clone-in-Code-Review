		CLIGitCommand bean = new CLIGitCommand();
		final CmdLineParser clp = new TestCmdLineParser(bean);
		clp.parseArgument(argv);

		final TextBuiltin cmd = bean.getSubcommand();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		cmd.outs = baos;
		ByteArrayOutputStream errs = new ByteArrayOutputStream();
		cmd.errs = errs;
		boolean seenHelp = TextBuiltin.containsHelp(argv);
		if (cmd.requiresRepository())
			cmd.init(db, null);
		else
			cmd.init(null, null);
		try {
			cmd.execute(bean.getArguments().toArray(
					new String[bean.getArguments().size()]));
		} catch (TerminatedByHelpException e) {
			seenHelp = true;
		} finally {
			if (cmd.outw != null) {
				cmd.outw.flush();
			}
			if (cmd.errw != null) {
				cmd.errw.flush();
			}
			if (seenHelp) {
				return errs.toByteArray();
			} else if (errs.size() > 0) {
				System.err.print(errs.toString());
			}
