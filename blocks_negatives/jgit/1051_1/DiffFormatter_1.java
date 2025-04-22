		if (noNewLineIndicator)
			writeNoNewLine(out);
	}

	private static void writeNoNewLine(final OutputStream out)
			throws IOException {
		out.write(noNewLine);
