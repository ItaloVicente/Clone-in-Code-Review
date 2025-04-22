	public static boolean equals(boolean left, boolean right) {
		return left == right;
	}

	public static boolean equals(int left, int right) {
		return left == right;
	}

	public static boolean equals(Object left, Object right) {
		return left == null ? right == null : ((right != null) && left.equals(right));
	}

	/**
	 * Tests whether two arrays of objects are equal to each other. The arrays must
	 * not be <code>null</code>, but their elements may be <code>null</code>.
	 *
	 * @param leftArray  The left array to compare; may be <code>null</code>, and
	 *                   may be empty and may contain <code>null</code> elements.
	 * @param rightArray The right array to compare; may be <code>null</code>, and
	 *                   may be empty and may contain <code>null</code> elements.
	 * @return <code>true</code> if the arrays are equal length and the elements at
	 *         the same position are equal; <code>false</code> otherwise.
	 */
	public static boolean equals(final Object[] leftArray, final Object[] rightArray) {
		if (leftArray == rightArray) {
			return true;
		}

		if (leftArray == null) {
			return (rightArray == null);
		} else if (rightArray == null) {
			return false;
		}

		if (leftArray.length != rightArray.length) {
			return false;
		}

		for (int i = 0; i < leftArray.length; i++) {
			final Object left = leftArray[i];
			final Object right = rightArray[i];
			final boolean equal = (left == null) ? (right == null) : (left.equals(right));
			if (!equal) {
				return false;
			}
		}

		return true;
	}

	public static int hashCode(boolean b) {
		return b ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode();
	}

	public static int hashCode(int i) {
		return i;
	}

	public static int hashCode(Object object) {
		return object != null ? object.hashCode() : 0;
	}

