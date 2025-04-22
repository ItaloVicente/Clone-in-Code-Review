	static String chomp(String line) {
		if (line.charAt(line.length() - 1) == '\n') {
			return line.substring(0
		}
		return line;
	}

	static ReceiveCommand parseCommand(String line) {
		final ObjectId oldId = ObjectId.fromString(line.substring(0
		final ObjectId newId = ObjectId.fromString(line.substring(41
		String name = line.substring(82);
		final ReceiveCommand cmd = new ReceiveCommand(oldId
		return cmd;
	}

