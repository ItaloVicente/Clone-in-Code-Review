	public static class SmallObject extends ObjectLoader {
		private final int type;

		private final byte[] data;

		public SmallObject(int type
			this.type = type;
			this.data = data;
		}

		@Override
		public int getType() {
			return type;
		}

		@Override
		public long getSize() {
			return getCachedBytes().length;
		}

		@Override
		public boolean isLarge() {
			return false;
		}

		@Override
		public byte[] getCachedBytes() {
			return data;
		}

		@Override
		public ObjectStream openStream() {
			return new ObjectStream.SmallStream(this);
		}
	}
