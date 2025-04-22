    }

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
