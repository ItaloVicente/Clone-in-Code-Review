	@Override
	public List<String> getHeaderFields(@NonNull String name) {
		Map<String
		List<String> fields = mapValuesToListIgnoreCase(name
		return fields;
	}

	private static List<String> mapValuesToListIgnoreCase(String keyName
			Map<String
		List<String> fields = new LinkedList<>();
		m.entrySet().stream().filter(e -> keyName.equalsIgnoreCase(e.getKey()))
				.forEach(e -> fields.addAll(e.getValue()));
		return fields;
	}

