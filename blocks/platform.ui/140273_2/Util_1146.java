			return compare(System.identityHashCode(left), System.identityHashCode(right));
		}
	}

	public static int compareIdentity(Object left, Object right) {
		if (left == null && right == null) {
