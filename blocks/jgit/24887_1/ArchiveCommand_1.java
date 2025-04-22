	private static final ConcurrentMap<String
			new ConcurrentHashMap<String

	private static <K
			K key
			return true;

		if (oldValue == null)
			return map.putIfAbsent(key
		else if (newValue == null)
			return map.remove(key
		else
			return map.replace(key
	}
