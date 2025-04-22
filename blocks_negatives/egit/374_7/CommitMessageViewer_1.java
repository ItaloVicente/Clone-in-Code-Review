	private RawText getRawText(ObjectId id) throws IOException {
		if (id.equals(ObjectId.zeroId()))
			return new RawText(new byte[] { });
		return new RawText(db.openBlob(id).getCachedBytes());
	}

