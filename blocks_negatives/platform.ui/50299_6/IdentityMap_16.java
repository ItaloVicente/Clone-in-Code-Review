	public void putAll(Map other) {
		for (Iterator iterator = other.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			wrappedMap.put(IdentityWrapper.wrap(entry.getKey()), entry
					.getValue());
