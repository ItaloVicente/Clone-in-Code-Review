	private boolean loadSize(ObjectToPack e) throws MissingObjectException,
			IncorrectObjectTypeException, IOException {
		long sz = reader.getObjectSize(e, e.getType());

		if (config.getBigFileThreshold() <= sz || Integer.MAX_VALUE <= sz)
			return false;

		if (sz <= DeltaIndex.BLKSZ)
			return false;

		e.setWeight((int) sz);
		return true;
	}

