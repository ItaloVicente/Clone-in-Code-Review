	private static class HashMapEntry {
		Object key, value;

		HashMapEntry next;

		HashMapEntry(Object theKey, Object theValue) {
			key = theKey;
			value = theValue;
		}
	}

	private static final class EmptyEnumerator implements Enumeration {
		@Override
