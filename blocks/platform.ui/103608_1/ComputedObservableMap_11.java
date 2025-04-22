			oldValues.put(removedKey, oldValue);
		}
		for (K addedKey : addedKeys) {
			V newValue = null;
			if (addedKey != null) {
				newValue = doGet(addedKey);
				hookListener(addedKey);
				knownKeys.add(addedKey);
