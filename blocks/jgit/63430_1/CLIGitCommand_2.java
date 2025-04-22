	private static List<String> getOutput(Result result) {
		if (result.ex instanceof TerminatedByHelpException) {
			return result.errLines();
		}
		return result.outLines();
	}

	private void run(String commandLine) throws Exception {
		String[] argv = convertToMainArgs(commandLine);
		try {
			super.run(argv);
		} catch (TerminatedByHelpException e) {
		} finally {
			errw.flush();
		}
	}

	private static String[] convertToMainArgs(String str)
