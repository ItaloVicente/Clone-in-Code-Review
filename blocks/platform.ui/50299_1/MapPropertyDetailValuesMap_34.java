	protected Map<K, T> doGetMap(S source) {
		Map<K, V> masterMap = masterProperty.getMap(source);
		Map<K, T> detailMap = new IdentityMap<K, T>();
		for (Map.Entry<K, V> entry : masterMap.entrySet()) {
			detailMap.put(entry.getKey(),
					detailProperty.getValue(entry.getValue()));
