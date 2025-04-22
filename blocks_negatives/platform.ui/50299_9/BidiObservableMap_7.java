	private void addMapping(Object key, Object value) {
		if (!valuesToKeys.containsKey(value)) {
			if (key instanceof Set)
				key = new HashSet(Collections.singleton(key));
			valuesToKeys.put(value, key);
		} else {
			Object elementOrSet = valuesToKeys.get(value);
			Set set;
			if (elementOrSet instanceof Set) {
				set = (Set) elementOrSet;
			} else {
				set = new HashSet(Collections.singleton(elementOrSet));
				valuesToKeys.put(value, set);
			}
