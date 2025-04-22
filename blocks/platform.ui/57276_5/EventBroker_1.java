	private static <K, V> Map<K, V> convertToMap(Dictionary<K, V> source) {
		Map<K, V> map = new Hashtable<>();
		for (Enumeration<K> keys = source.keys(); keys.hasMoreElements();) {
			K key = keys.nextElement();
			map.put(key, source.get(key));
		}
		return map;
	}

