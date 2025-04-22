	private void parseShallow(String idStr) throws PackProtocolException {
		ObjectId id;
		try {
			id = ObjectId.fromString(idStr);
		} catch (InvalidObjectIdException e) {
			throw new PackProtocolException(e.getMessage()
		}
		clientShallowCommits.add(id);
	}

