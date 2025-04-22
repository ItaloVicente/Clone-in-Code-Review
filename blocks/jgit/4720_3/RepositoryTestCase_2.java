
	protected String[] execute(String... cmds) throws Exception {
		List<String> result = new ArrayList<String>(cmds.length);
		for (String cmd : cmds) {
			result.addAll(CLIGitCommand.execute(cmd
		}
		return result.toArray(new String[0]);
	}
