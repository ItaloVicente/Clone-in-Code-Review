	static ReceiveCommand parseCommand(String line) {
		final ObjectId oldId = ObjectId.fromString(line.substring(0
		final ObjectId newId = ObjectId.fromString(line.substring(41
		String name = line.substring(82);
		if (!name.isEmpty() && name.charAt(name.length() - 1) == '\n') {
			name = name.substring(0
		}
		final ReceiveCommand cmd = new ReceiveCommand(oldId
		return cmd;
	}

