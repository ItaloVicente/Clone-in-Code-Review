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

    /**
     * Makes a type-safe copy of the given map. This method should be used when
     * a map is crossing an API boundary (i.e., from a hostile plug-in into
     * internal code, or vice versa).
     *
     * @param map
     *            The map which should be copied; must not be <code>null</code>.
     * @param keyClass
     *            The class that all the keys must be; must not be
     *            <code>null</code>.
     * @param valueClass
     *            The class that all the values must be; must not be
     *            <code>null</code>.
     * @param allowNullKeys
     *            Whether <code>null</code> keys should be allowed.
     * @param allowNullValues
     *            Whether <code>null</code> values should be allowed.
     * @return A copy of the map; may be empty, but never <code>null</code>.
     */
