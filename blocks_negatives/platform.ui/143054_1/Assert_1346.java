    /**
     * Asserts that an argument is legal. If the given boolean is
     * not <code>true</code>, an <code>IllegalArgumentException</code>
     * is thrown.
     *
     * @param expression the outcome of the check
     * @return <code>true</code> if the check passes (does not return
     *    if the check fails)
     * @exception IllegalArgumentException if the legality test failed
     */
    public static boolean isLegal(boolean expression) {
        if (expression) {
            return true;
        }
        return isLegal(expression, "");//$NON-NLS-1$
    }
