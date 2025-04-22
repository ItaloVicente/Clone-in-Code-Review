	static ReceiveCommand parseCommand(String line) throws PackProtocolException {
          if (line == null || line.length() < 83) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef);
		}
		String oldStr = line.substring(0, 40);
		String newStr = line.substring(41, 81);
		ObjectId oldId, newId;
		try {
			oldId = ObjectId.fromString(oldStr);
			newId = ObjectId.fromString(newStr);
		} catch (InvalidObjectIdException e) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef, e);
		}
		String name = line.substring(82);
		if (!Repository.isValidRefName(name)) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef);
		}
		return new ReceiveCommand(oldId, newId, name);
	}

