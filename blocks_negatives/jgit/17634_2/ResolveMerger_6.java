	private static RawText getRawText(ObjectId id, Repository db)
			throws IOException {
		if (id.equals(ObjectId.zeroId()))
			return new RawText(new byte[] {});
		return new RawText(db.open(id, Constants.OBJ_BLOB).getCachedBytes());
	}

