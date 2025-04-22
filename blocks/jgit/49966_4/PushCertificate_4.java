
	public List<ReceiveCommand> getCommands() {
		return commands;
	}

	@Deprecated
	public String getCommandList() {
		return rawCommands;
	}

	public String getSignature() {
		return signature;
	}

	public String toText() {
		return new StringBuilder()
				.append(VERSION).append(' ').append(version).append('\n')
				.append(PUSHER).append(' ').append(pusher.toExternalString())
				.append('\n')
				.append(PUSHEE).append(' ').append(pushee).append('\n')
				.append(NONCE).append(' ').append(nonce).append('\n')
				.append('\n')
				.append(rawCommands)
				.toString();
	}
