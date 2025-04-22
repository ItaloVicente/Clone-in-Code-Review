	private void removeMapping(Object key, Object value) {
		if (valuesToKeys.containsKey(value)) {
			Object elementOrSet = valuesToKeys.get(value);
			if (elementOrSet instanceof Set) {
				Set set = (Set) elementOrSet;
				set.remove(key);
				if (set.isEmpty()) {
					valuesToKeys.remove(value);
				}
			} else if (elementOrSet == key
					|| (elementOrSet != null && elementOrSet.equals(key))) {
				valuesToKeys.remove(value);
