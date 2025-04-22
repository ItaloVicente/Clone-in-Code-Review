	public BlobBasedConfig(Config base
			throws IOException
		this(base
	}

	private static byte[] read(Repository db
			throws MissingObjectException
			IOException {
		ObjectReader or = db.newObjectReader();
		try {
			return read(or
		} finally {
			or.release();
		}
	}

	private static byte[] read(ObjectReader or
			throws MissingObjectException
			IOException {
		ObjectLoader loader = or.open(blobId
		if (loader.isLarge()) {
			ObjectStream in = loader.openStream();
			try {
				byte[] buf = new byte[(int) in.getSize()];
				IO.readFully(in
				return buf;
			} finally {
				in.close();
			}
		}
		return loader.getCachedBytes();
