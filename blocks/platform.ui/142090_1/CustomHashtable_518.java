		return new HashEnumerator(false);
	}

	public Object get(Object key) {
		int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
		HashMapEntry entry = elementData[index];
		while (entry != null) {
			if (keyEquals(key, entry.key)) {
