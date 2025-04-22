	public static List<String> execute(String str
			throws Exception {
		String[] args = split(str);
		if (!args[0].equalsIgnoreCase("git") || args.length < 2)
			throw new IllegalArgumentException(
					"Expected 'git <command> [<args>]'
