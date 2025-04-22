    /**
     * Decides whether two booleans are equal.
     *
     * @param left
     *            The first boolean to compare; may be <code>null</code>.
     * @param right
     *            The second boolean to compare; may be <code>null</code>.
     * @return <code>true</code> if the booleans are equal; <code>false</code>
     *         otherwise.
     */
    public static final boolean equals(final boolean left, final boolean right) {
        return left == right;
    }

    /**
     * Decides whether two objects are equal -- defending against
     * <code>null</code>.
     *
     * @param left
     *            The first object to compare; may be <code>null</code>.
     * @param right
     *            The second object to compare; may be <code>null</code>.
     * @return <code>true</code> if the objects are equals; <code>false</code>
     *         otherwise.
     */
    public static final boolean equals(final Object left, final Object right) {
		return left == null ? right == null : ((right != null) && left.equals(right));
    }

	/**
	 * Tests whether two arrays of objects are equal to each other. The arrays
	 * must not be <code>null</code>, but their elements may be
	 * <code>null</code>.
	 *
	 * @param leftArray
	 *            The left array to compare; may be <code>null</code>, and
	 *            may be empty and may contain <code>null</code> elements.
	 * @param rightArray
	 *            The right array to compare; may be <code>null</code>, and
	 *            may be empty and may contain <code>null</code> elements.
	 * @return <code>true</code> if the arrays are equal length and the
	 *         elements at the same position are equal; <code>false</code>
	 *         otherwise.
	 */
	public static final boolean equals(final Object[] leftArray, final Object[] rightArray) {
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

    /**
     * Computes the hash code for an integer.
     *
     * @param i
     *            The integer for which a hash code should be computed.
     * @return <code>i</code>.
     */
    public static final int hashCode(final int i) {
        return i;
    }

    /**
     * Computes the hash code for an object, but with defense against
     * <code>null</code>.
     *
     * @param object
     *            The object for which a hash code is needed; may be
     *            <code>null</code>.
     * @return The hash code for <code>object</code>; or <code>0</code> if
     *         <code>object</code> is <code>null</code>.
     */
    public static final int hashCode(final Object object) {
        return object != null ? object.hashCode() : 0;
    }

