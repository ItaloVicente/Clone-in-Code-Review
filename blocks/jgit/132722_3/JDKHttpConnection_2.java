		TreeMap<String
				String.CASE_INSENSITIVE_ORDER);
		for (Entry<String
				.getHeaderFields().entrySet()) {
			List<String> values = ignoredCaseMap.get(e.getKey());
			if (values == null) {
				ignoredCaseMap.put(e.getKey()
			} else {
				LinkedList<String> newValues = new LinkedList<>(values);
				newValues.addAll(e.getValue());
				ignoredCaseMap.put(e.getKey()
			}
		}
		return Collections.unmodifiableMap(ignoredCaseMap);
