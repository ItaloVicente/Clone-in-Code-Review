	static String chomp(String line) {
		if (line != null && !line.isEmpty()
				&& line.charAt(line.length() - 1) == '\n') {
			return line.substring(0
		}
		return line;
	}

	static ReceiveCommand parseCommand(String line) {
		ObjectId oldId = ObjectId.fromString(line.substring(0
		ObjectId newId = ObjectId.fromString(line.substring(41
		String name = line.substring(82);
		return new ReceiveCommand(oldId
	}

