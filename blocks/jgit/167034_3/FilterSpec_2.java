	static class ObjectTypes {
		static ObjectTypes ALL = allow(OBJ_BLOB

		private final BigInteger val;

		private ObjectTypes(BigInteger val) {
			this.val = val;
		}

		static ObjectTypes allow(int... types) {
			BigInteger bits = BigInteger.valueOf(0);
			for (int type : types) {
				bits = bits.setBit(type);
			}
			return new ObjectTypes(bits);
		}

		boolean contains(int type) {
			return val.testBit(type);
		}
	}

	private final ObjectTypes types;

