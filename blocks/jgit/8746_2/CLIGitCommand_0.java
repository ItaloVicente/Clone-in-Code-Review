		try {
			return IO.readLines(new String(rawExecute(str
		} catch (Die e) {
			return IO.readLines(MessageFormat.format(CLIText.get().fatalError
					e.getMessage()));
		}
	}

	public static byte[] rawExecute(String str
			throws Exception {
