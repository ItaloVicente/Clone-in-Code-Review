	class NullIterator<E> implements Iterator<MemcachedNode> {

		public boolean hasNext() {
			return false;
		}

		public MemcachedNode next() {
			throw new NoSuchElementException("VBucketNodeLocators have no alternate nodes.");
		}

		public void remove() {
			throw new UnsupportedOperationException("VBucketNodeLocators have no alternate nodes; cannot remove.");
		}
