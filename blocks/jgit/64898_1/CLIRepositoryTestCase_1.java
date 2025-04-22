	protected String[] executeUnchecked(String... cmds) throws Exception {
		List<String> result = new ArrayList<String>(cmds.length);
		for (String cmd : cmds) {
			result.addAll(CLIGitCommand.executeUnchecked(cmd
		}
		return result.toArray(new String[0]);
	}

