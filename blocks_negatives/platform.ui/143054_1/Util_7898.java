    /**
     * Compares two comparable objects, but with protection against
     * <code>null</code>.
     *
     * @param left
     *            The left value to compare; may be <code>null</code>.
     * @param right
     *            The right value to compare; may be <code>null</code>.
     * @return <code>-1</code> if <code>left</code> is <code>null</code>
     *         and <code>right</code> is not <code>null</code>;
     *         <code>0</code> if they are both <code>null</code>;
     *         <code>1</code> if <code>left</code> is not <code>null</code>
     *         and <code>right</code> is <code>null</code>. Otherwise, the
     *         result of <code>left.compareTo(right)</code>.
     */
