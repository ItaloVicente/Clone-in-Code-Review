		int pos = levelTwoPosition(objId
		if (pos < 0) {
			return -1;
		}
		int b0 = data[pos - 4] & 0xff;
		int b1 = data[pos - 3] & 0xff;
		int b2 = data[pos - 2] & 0xff;
		int b3 = data[pos - 1] & 0xff;
		return (((long) b0) << 24) | (b1 << 16) | (b2 << 8) | (b3);
	}

	@Override
	public int findPosition(AnyObjectId objId) {
		int levelOne = objId.getFirstByte();
		int levelTwo = levelTwoPosition(objId
		if (levelTwo < 0) {
