		for (String cmd : cmds) {
			Result r = CLIGitCommand.executeRaw(cmd
			if (r.ex instanceof TerminatedByHelpException) {
				result.addAll(r.errLines());
			} else if (r.ex != null) {
				throw r.ex;
			}
			result.addAll(r.outLines());
		}
