		this.comparer = comparer;
	}

	public CustomHashtable(CustomHashtable table, IElementComparer comparer) {
		this(table.size() * 2, comparer);
		for (int i = table.elementData.length; --i >= 0;) {
			HashMapEntry entry = table.elementData[i];
			while (entry != null) {
				put(entry.key, entry.value);
				entry = entry.next;
			}
		}
	}

	public IElementComparer getComparer() {
		return comparer;
	}

	private void computeMaxSize() {
		threshold = (int) (elementData.length * loadFactor);
	}

	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	public Enumeration elements() {
		if (elementCount == 0) {
