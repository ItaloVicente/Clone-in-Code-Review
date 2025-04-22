	@Override
	public void clear() {
		hash = hash(new byte[0]);
		super.clear();
	}

	private static ObjectId hash(final byte[] rawText) {
		return ObjectId.fromRaw(Constants.newMessageDigest().digest(rawText));
	}

