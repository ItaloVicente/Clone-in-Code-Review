	@Override
	public List<String> getHeaderFields(String name) {
		Map<String
		List<String> fields = mapValuesToListIgnoreCase(name
		return fields;
	}

	static List<String> mapValuesToListIgnoreCase(String keyName
			Map<String
		Set<String> keys = m.keySet();
		List<String> fields = new LinkedList<>();
		for (String k : keys) {
			if (k.equalsIgnoreCase(keyName)) {
				fields.addAll(m.get(k));
			}
		}
		return fields;
	}

