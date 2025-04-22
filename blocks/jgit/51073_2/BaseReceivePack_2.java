	static ReceiveCommand parseCommand(String line) throws PackProtocolException {
		if (line.length() < 83) {
			String m = JGitText.get().errorInvalidProtocolWantedOldNewRef;
			throw new PackProtocolException(m);
		}
		String oldStr = line.substring(0
		String newStr = line.substring(41
		ObjectId oldId
		try {
			oldId = ObjectId.fromString(oldStr);
			newId = ObjectId.fromString(newStr);
		} catch (IllegalArgumentException e) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef
		}
