			String key = (String) keys.next();
			Object next = cache.get(key);
			if (next instanceof Collection) {
				Collection collection = (Collection) next;
				if (collection.contains(value)) {
					collection.remove(value);
					if (collection.isEmpty()) {
						keysToRemove.add(key);
					}
					break;
				}
			} else {
				if (cache.get(key).equals(value)) {
					keysToRemove.add(key);
				}
