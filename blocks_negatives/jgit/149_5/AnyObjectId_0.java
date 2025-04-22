	static final int RAW_LEN = Constants.OBJECT_ID_LENGTH;

	static final int STR_LEN = RAW_LEN * 2;

	static {
		if (RAW_LEN != 20)
			throw new LinkageError("ObjectId expects"
					+ " Constants.OBJECT_ID_LENGTH = 20; it is " + RAW_LEN
					+ ".");
	}
