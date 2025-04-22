	private String commandFor(final String exe) {
		String path = uri.getPath();
		if (uri.getScheme() != null && uri.getPath().startsWith("/~"))
			path = (uri.getPath().substring(1));

		final StringBuilder cmd = new StringBuilder();
		final int gitspace = exe.indexOf("git ");
		if (gitspace >= 0) {
			sqMinimal(cmd
			cmd.append(' ');
			sqMinimal(cmd
		} else
			sqMinimal(cmd
		cmd.append(' ');
		sqAlways(cmd
		return cmd.toString();
	}
