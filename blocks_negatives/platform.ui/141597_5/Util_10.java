	/**
	 * Checks whether the two objects are <code>null</code> -- allowing for
	 * <code>null</code>.
	 *
	 * @param left
	 *            The left object to compare; may be <code>null</code>.
	 * @param right
	 *            The right object to compare; may be <code>null</code>.
	 * @return <code>true</code> if the two objects are equivalent;
	 *         <code>false</code> otherwise.
	 * @deprecated Use {@link Objects#equals(Object, Object)}
	 */
	@Deprecated
	public static boolean equals(final Object left, final Object right) {
		return left == null ? right == null : ((right != null) && left.equals(right));
	}

	/**
	 * Tests whether two arrays of objects are equal to each other. The arrays must
	 * not be <code>null</code>, but their elements may be <code>null</code>.
	 *
	 * @param leftArray
	 *            The left array to compare; may be <code>null</code>, and may be
	 *            empty and may contain <code>null</code> elements.
	 * @param rightArray
	 *            The right array to compare; may be <code>null</code>, and may be
	 *            empty and may contain <code>null</code> elements.
	 * @return <code>true</code> if the arrays are equal length and the elements at
	 *         the same position are equal; <code>false</code> otherwise.
	 * @deprecated Use {@link Arrays#equals(Object[], Object[])}
	 */
	@Deprecated
	public static boolean equals(final Object[] leftArray, final Object[] rightArray) {
		return Arrays.equals(leftArray, rightArray);
	}

	/**
	 * Provides a hash code based on the given integer value.
	 *
	 * @param i
	 *            The integer value
	 * @return <code>i</code>
	 */
	public static int hashCode(final int i) {
		return i;
	}

	/**
	 * Provides a hash code for the object -- defending against <code>null</code>.
	 *
	 * @param object
	 *            The object for which a hash code is required.
	 * @return <code>object.hashCode</code> or <code>0</code> if <code>object</code> if
	 *         <code>null</code>.
	 */
	public static int hashCode(final Object object) {
		return object != null ? object.hashCode() : 0;
	}

	/**
	 * Computes the hash code for an array of objects, but with defense against <code>null</code>.
	 *
	 * @param objects
	 *            The array of objects for which a hash code is needed; may be <code>null</code>.
	 * @return The hash code for <code>objects</code>; or <code>0</code> if <code>objects</code> is
	 *         <code>null</code>.
	 */
	public static int hashCode(final Object[] objects) {
		if (objects == null) {
			return 0;
		}

		int hashCode = 89;
		for (final Object object : objects) {
			if (object != null) {
				hashCode = hashCode * 31 + object.hashCode();
			}
		}

		return hashCode;
	}

