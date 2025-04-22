	public static final ObjectId fromStringCheck(final byte[] buf
		try {
			return fromHexString(buf
		}
		catch (InvalidObjectIdException ex) {
			throw new IOException(ex);
		}
	}

