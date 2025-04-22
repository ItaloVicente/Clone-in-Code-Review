			return compare(System.identityHashCode(left), System
                    .identityHashCode(right));
		}
    }

    /**
     * An optimized comparison that uses identity hash codes to perform the
     * comparison between non- <code>null</code> objects.
     *
     * @param left
     *            The left-hand side of the comparison; may be <code>null</code>.
     * @param right
     *            The right-hand side of the comparison; may be
     *            <code>null</code>.
     * @return <code>0</code> if they are the same, <code>-1</code> if left
     *         is <code>null</code>;<code>1</code> if right is
     *         <code>null</code>. Otherwise, the left identity hash code
     *         minus the right identity hash code.
     */
    public static int compareIdentity(Object left, Object right) {
        if (left == null && right == null) {
