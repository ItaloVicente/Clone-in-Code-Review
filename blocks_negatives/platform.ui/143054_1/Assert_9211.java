    /**
     * Asserts that the given boolean is <code>true</code>. If this
     * is not the case, some kind of unchecked exception is thrown.
     *
     * @param expression the outcome of the check
     * @return <code>true</code> if the check passes (does not return
     *    if the check fails)
     */
    public static boolean isTrue(boolean expression) {
        if (expression) {
            return true;
        }
        return isTrue(expression, "");//$NON-NLS-1$
    }
