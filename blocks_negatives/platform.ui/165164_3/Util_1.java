	public static boolean endsWith(Object[] left, Object[] right, boolean equals) {
		if (left == null || right == null) {
			return false;
		}
		int l = left.length;
		int r = right.length;

		if (r > l || !equals && r == l) {
			return false;
		}

		for (int i = 0; i < r; i++) {
			if (!Objects.equals(left[l - i - 1], right[r - i - 1])) {
				return false;
			}
		}

		return true;
	}

	public static Collection safeCopy(Collection collection, Class c) {
		return safeCopy(collection, c, false);
	}

	public static Collection safeCopy(Collection collection, Class c, boolean allowNullElements) {
		if (collection == null || c == null) {
			throw new NullPointerException();
		}

		collection = Collections.unmodifiableCollection(new ArrayList(collection));
		Iterator iterator = collection.iterator();

		while (iterator.hasNext()) {
			assertInstance(iterator.next(), c, allowNullElements);
		}

		return collection;
	}

