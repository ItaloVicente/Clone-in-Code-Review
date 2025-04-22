		DescribeCommand cmd = new Git(db).describe();
		if (tree != null)
			cmd.setTarget(tree);
		cmd.setLong(longDesc);
		String result = null;
		try {
			result = cmd.call();
		} catch (RefNotFoundException e) {
			throw die(CLIText.get().noNamesFound, e);
		}
		if (result == null)
			throw die(CLIText.get().noNamesFound);
