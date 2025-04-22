		PrintStream hookErrRedirect = null;
		try {
			hookErrRedirect = new PrintStream(stderrStream, false,
					UTF_8.name());
		} catch (UnsupportedEncodingException e) {
		}
