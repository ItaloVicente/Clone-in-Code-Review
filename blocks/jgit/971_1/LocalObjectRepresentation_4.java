		if (baseId == null && getFormat() == PACK_DELTA) {
			try {
				baseId = pack.findObjectForOffset(baseOffset);
			} catch (IOException error) {
				return null;
			}
		}
		return baseId;
	}

	private static final class Delta extends LocalObjectRepresentation {
		@Override
		public int getFormat() {
			return PACK_DELTA;
