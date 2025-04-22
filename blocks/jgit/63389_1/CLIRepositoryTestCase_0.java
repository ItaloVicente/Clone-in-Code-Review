	protected String[] execute(String... cmds) throws Exception {
		List<String> result = new ArrayList<String>(cmds.length);
		for (String cmd : cmds) {
			List<String> out = CLIGitCommand.execute(cmd
			if (contains(out
				throw new Die(toString(out));
			}
			result.addAll(out);
		}
		return result.toArray(new String[0]);
	}

