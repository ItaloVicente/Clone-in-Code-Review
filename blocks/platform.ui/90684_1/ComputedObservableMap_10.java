			oldValues.put(removedKey, oldValue);
		}
		for (Iterator<K> it2 = addedKeys.iterator(); it2.hasNext();) {
			K addedKey = it2.next();
			V newValue = null;
			if (addedKey != null) {
				newValue = doGet(addedKey);
				hookListener(addedKey);
				knownKeys.add(addedKey);
