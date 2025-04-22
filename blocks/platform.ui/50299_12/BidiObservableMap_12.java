	private void addMapping(K key, V value) {
		if (valuesToSingleKeys.containsKey(value)) {
			K element = valuesToSingleKeys.get(value);
			Set<K> set = new HashSet<>(Collections.singleton(element));
			valuesToSingleKeys.remove(value);
			valuesToSetsOfKeys.put(value, set);
