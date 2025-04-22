    /**
     * Compares two objects that are not otherwise comparable. If neither object
     * is <code>null</code>, then the string representation of each object is
     * used.
     *
     * @param left
     *            The left value to compare. The string representation of this
     *            value must not be <code>null</code>.
     * @param right
     *            The right value to compare. The string representation of this
     *            value must not be <code>null</code>.
     * @return <code>-1</code> if <code>left</code> is <code>null</code>
     *         and <code>right</code> is not <code>null</code>;
     *         <code>0</code> if they are both <code>null</code>;
     *         <code>1</code> if <code>left</code> is not <code>null</code>
     *         and <code>right</code> is <code>null</code>. Otherwise, the
     *         result of
     *         <code>left.toString().compareTo(right.toString())</code>.
     */
    public static final int compare(final Object left, final Object right) {
        if (left == null && right == null) {
