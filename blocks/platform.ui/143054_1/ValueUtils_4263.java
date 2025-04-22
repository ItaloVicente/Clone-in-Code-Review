		collection = getValue(collection);
		Object value = collection;
		if (collection != null) {
			if (collection.getClass().isArray()) {
				if (index < 0 || index >= Array.getLength(collection)) {
					return null;
				}
				value = Array.get(collection, index);
			}
			else if (collection instanceof List) {
				if (index < 0 || index >= ((List<?>) collection).size()) {
					return null;
				}
				value = ((List<Object>) collection).get(index);
			}
			else if (collection instanceof Collection) {
				int i = 0;
				Iterator<Object> it = ((Collection<Object>) collection).iterator();
				for (; i < index; i++) {
					it.next();
				}
				if (it.hasNext()) {
					value = it.next();
				}
				else {
					value = null;
				}
			}
		}
		return value;
	}
