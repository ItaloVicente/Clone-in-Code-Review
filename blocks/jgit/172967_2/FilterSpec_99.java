	static class ObjectTypes {
		static ObjectTypes ALL = allow(OBJ_BLOB

		private final BigInteger val;

		private ObjectTypes(BigInteger val) {
			this.val = requireNonNull(val);
		}

		static ObjectTypes allow(int... types) {
			BigInteger bits = ZERO;
			for (int type : types) {
				bits = bits.setBit(type);
			}
			return new ObjectTypes(bits);
		}

		boolean contains(int type) {
			return val.testBit(type);
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ObjectTypes)) {
				return false;
			}

			ObjectTypes other = (ObjectTypes) obj;
			return other.val.equals(val);
		}

		@Override
		public int hashCode() {
			return val.hashCode();
		}
	}

	private final ObjectTypes types;

