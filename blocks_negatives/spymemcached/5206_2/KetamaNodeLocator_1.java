	class KetamaIterator implements Iterator<MemcachedNode> {

		final String key;
		long hashVal;
		int remainingTries;
		int numTries=0;

		public KetamaIterator(final String k, final int t) {
			super();
			hashVal=hashAlg.hash(k);
			remainingTries=t;
			key=k;
		}

		private void nextHash() {
			long tmpKey=hashAlg.hash((numTries++) + key);
			hashVal += (int)(tmpKey ^ (tmpKey >>> 32));
			hashVal &= 0xffffffffL; /* truncate to 32-bits */
			remainingTries--;
		}
