	public Map<String
		Map<String
		for (String name : refs) {
			Ref ref = exactRef(name);
			if (ref != null) {
				result.put(name
			}
		}
		return result;
	}

	public Ref firstExactRef(String... refs) throws IOException {
		for (String name : refs) {
			Ref ref = exactRef(name);
			if (ref != null) {
				return ref;
			}
		}
		return null;
	}

