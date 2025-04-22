	public void printUsageAndExit(final String message, final CmdLineParser clp) {
		PrintWriter writer = new PrintWriter(System.err);
		writer.println(message);
		writer.print(commandName);
		clp.printSingleLineUsage(writer, getResourceBundle());
		writer.println();

		writer.println();
		clp.printUsage(writer, getResourceBundle());
		writer.println();

		writer.flush();
