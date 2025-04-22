		return new HashEnumerator(true);
	}

	public Object put(Object key, Object value) {
		if (key != null && value != null) {
			int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
			HashMapEntry entry = elementData[index];
			while (entry != null && !keyEquals(key, entry.key)) {
