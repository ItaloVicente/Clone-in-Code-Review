	public static SortedMap safeCopy(SortedMap sortedMap, Class keyClass, Class valueClass) {
		return safeCopy(sortedMap, keyClass, valueClass, false, false);
	}

	public static SortedMap safeCopy(SortedMap sortedMap, Class keyClass, Class valueClass, boolean allowNullKeys,
			boolean allowNullValues) {
		if (sortedMap == null || keyClass == null || valueClass == null) {
			throw new NullPointerException();
		}

		sortedMap = Collections.unmodifiableSortedMap(new TreeMap(sortedMap));
		Iterator iterator = sortedMap.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			assertInstance(entry.getKey(), keyClass, allowNullKeys);
			assertInstance(entry.getValue(), valueClass, allowNullValues);
		}

		return sortedMap;
	}

