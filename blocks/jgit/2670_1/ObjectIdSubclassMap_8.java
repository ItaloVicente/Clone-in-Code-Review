		V get(AnyObjectId toFind) {
			int h = hash(toFind);
			int n = CHAIN_LENGTH;
			V obj;
			while ((obj = members[h]) != null) {
				if (AnyObjectId.equals(obj
					return obj;
				if (++h == SIZE)
					h = 0;
				if (--n == 0)
					return null;
			}
			return null;
		}

		private static int hash(AnyObjectId objId) {
			return objId.w3 >>> (32 - BITS);
		}

		@SuppressWarnings("unchecked")
		private static final <V> V[] createArray(int sz) {
			return (V[]) new ObjectId[sz];
		}
