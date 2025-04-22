
	private static Set<ObjectId> haves(ObjectId... objects) {
		return asSet(objects);
	}

	private static Set<ObjectId> wants(ObjectId... objects) {
		return asSet(objects);
	}

	private static Set<ObjectId> shallows(ObjectId... objects) {
		return asSet(objects);
	}

	private static <T> Set<T> asSet(
			@SuppressWarnings("unchecked") T... objects) {
		Set<T> set = new HashSet<>();
		for (T o : objects) {
			set.add(o);
		}
		return set;
	}
