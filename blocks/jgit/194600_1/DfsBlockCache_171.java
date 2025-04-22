	private static final class EvictKey {
		private final int keyHash;
		private final int packExtPos;
		private final long position;

		EvictKey(Ref<?> ref) {
			keyHash = ref.key.hash;
			packExtPos = ref.key.packExtPos;
			position = ref.position;
		}

		@Override
		public boolean equals(Object object) {
			if (object instanceof EvictKey) {
				EvictKey other = (EvictKey) object;
				return keyHash == other.keyHash
						&& packExtPos == other.packExtPos
						&& position == other.position;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return DfsBlockCache.getInstance().hash(keyHash
		}
	}

