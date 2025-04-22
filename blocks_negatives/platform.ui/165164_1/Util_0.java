	public static void diff(Map<?, ?> left, Map<?, ?> right, Set leftOnly, Set different, Set rightOnly) {
		if (left == null || right == null || leftOnly == null || different == null || rightOnly == null) {
			throw new NullPointerException();
		}

		for (Entry<?, ?> leftEntry : left.entrySet()) {
			Object key = leftEntry.getKey();

			if (!right.containsKey(key)) {
				leftOnly.add(key);
			} else if (!Objects.equals(leftEntry.getValue(), right.get(key))) {
				different.add(key);
			}
		}

		for (Object rightKey : right.keySet()) {
			if (!left.containsKey(rightKey)) {
				rightOnly.add(rightKey);
			}
		}
	}

	public static void diff(Set left, Set right, Set leftOnly, Set rightOnly) {
		if (left == null || right == null || leftOnly == null || rightOnly == null) {
			throw new NullPointerException();
		}

		Iterator iterator = left.iterator();

		while (iterator.hasNext()) {
			Object object = iterator.next();

			if (!right.contains(object)) {
				leftOnly.add(object);
			}
		}

		iterator = right.iterator();

		while (iterator.hasNext()) {
			Object object = iterator.next();

			if (!left.contains(object)) {
				rightOnly.add(object);
			}
		}
	}

