
	public List<ReceiveCommand> getCommands() {
		return commands;
	}

	@Deprecated
	public String getCommandList() {
		return getCommandBuilder(commands).toString();
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
				.append(getCommandBuilder(commands))
				.toString();
	}

	static StringBuilder getCommandBuilder(
			Iterable<ReceiveCommand> commands) {
		StringBuilder sb = new StringBuilder();
		for (ReceiveCommand cmd : commands) {
			sb.append(cmd.getOldId().name())
					.append(' ').append(cmd.getNewId().name())
					.append(' ').append(cmd.getRefName()).append('\n');
		}
		return sb;
	}
