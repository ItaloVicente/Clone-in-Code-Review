
	public static Ref findRef(Map<String
		for (String prefix : SEARCH_PATH) {
			String fullname = prefix + name;
			Ref ref = map.get(fullname);
			if (ref != null)
				return ref;
		}
		return null;
	}
