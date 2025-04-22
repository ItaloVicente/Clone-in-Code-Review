	private void discardCommands() {
		if (sideBand) {
			long max = maxDiscardBytes;
			if (max < 0) {
				max = Math.max(3 * maxCommandBytes
			}
			try {
				new PacketLineIn(rawIn
			} catch (IOException e) {
			}
		}
	}

	private void parseShallow(String idStr) throws PackProtocolException {
		ObjectId id;
		try {
			id = ObjectId.fromString(idStr);
		} catch (InvalidObjectIdException e) {
			throw new PackProtocolException(e.getMessage()
		}
		clientShallowCommits.add(id);
	}
