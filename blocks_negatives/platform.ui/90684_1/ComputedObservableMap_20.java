			for (Iterator<K> it = addedKeys.iterator(); it.hasNext();) {
				K addedKey = it.next();
				V newValue = null;
				if (addedKey != null) {
					newValue = doGet(addedKey);
					hookListener(addedKey);
					knownKeys.add(addedKey);
				}
				newValues.put(addedKey, newValue);
