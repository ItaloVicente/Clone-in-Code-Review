	private void setHookCombinedOutput(CommitCommand commitCommand) {
		if (!redirectOutput)
			return;
		PrintStream ps = null;
		try {
			ps = new PrintStream(combinedHookOutput, false, UTF_8.name());
		} catch (UnsupportedEncodingException e) {
		}
		commitCommand.setHookOutputStream(ps);
		commitCommand.setHookErrorStream(ps);
	}

