		ReceiveCommand cmd;
		try {
			cmd = parseCommand(line);
		} catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
			throw new PackProtocolException(MessageFormat.format(
					JGitText.get().pushCertificateInvalidFieldValue
					"command"
		}
		commands.add(cmd);
