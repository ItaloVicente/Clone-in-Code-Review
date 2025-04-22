	static ReceiveCommand parseCommand(String line) throws PackProtocolException {
          if (line == null || line.length() < 83) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef);
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
