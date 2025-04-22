				HashMapEntry next = entry.next;
				entry.next = newData[index];
				newData[index] = entry;
				entry = next;
			}
		}
		elementData = newData;
		computeMaxSize();
	}

	public Object remove(Object key) {
		HashMapEntry last = null;
		int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
		HashMapEntry entry = elementData[index];
		while (entry != null && !keyEquals(key, entry.key)) {
			last = entry;
			entry = entry.next;
		}
		if (entry != null) {
			if (last == null) {
