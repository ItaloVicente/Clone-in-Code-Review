	private void removeMapping(Object key, V value) {
		if (valuesToSingleKeys.containsKey(value)) {
			K element = valuesToSingleKeys.get(value);
			if (element == key || (element != null && element.equals(key))) {
				valuesToSingleKeys.remove(value);
			}
		} else if (valuesToSetsOfKeys.containsKey(value)) {
			Set<K> keySet = valuesToSetsOfKeys.get(value);
			keySet.remove(key);
			if (keySet.isEmpty()) {
				valuesToSetsOfKeys.remove(value);
