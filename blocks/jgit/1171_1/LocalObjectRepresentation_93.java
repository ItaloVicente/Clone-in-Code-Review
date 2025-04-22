	@Override
	public ObjectId getDeltaBase() {
		if (baseId == null && getFormat() == PACK_DELTA) {
			try {
				baseId = pack.findObjectForOffset(baseOffset);
			} catch (IOException error) {
				return null;
			}
		}
		return baseId;
