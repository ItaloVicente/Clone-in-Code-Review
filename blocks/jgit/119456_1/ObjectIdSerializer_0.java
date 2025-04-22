	@NonNull
	public static ObjectId readNonNull(InputStream in) throws IOException {
		final byte[] b = new byte[OBJECT_ID_LENGTH];
		IO.readFully(in
		return ObjectId.fromRaw(b);
	}

