
	private static void checkCommandLine(String rawLine)
			throws PackProtocolException {
		if (rawLine == null
				|| rawLine.isEmpty()
				|| rawLine.charAt(rawLine.length() - 1) != '\n') {
			throw new PackProtocolException(MessageFormat.format(
					JGitText.get().pushCertificateInvalidFieldValue
					"command"
		}
	}
