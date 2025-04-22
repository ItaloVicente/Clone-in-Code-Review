    /**
     * Asserts that the given object is not <code>null</code>. If this
     * is not the case, some kind of unchecked exception is thrown.
     * <p>
     * As a general rule, parameters passed to API methods must not be
     * <code>null</code> unless <b>explicitly</b> allowed in the method's
     * specification. Similarly, results returned from API methods are never
     * <code>null</code> unless <b>explicitly</b> allowed in the method's
     * specification. Implementations are encouraged to make regular use of
     * <code>Assert.isNotNull</code> to ensure that <code>null</code>
     * parameters are detected as early as possible.
     * </p>
     *
     * @param object the value to test
     * @exception AssertionFailedException an unspecified unchecked exception if the object
     *   is <code>null</code>
     */
    public static void isNotNull(Object object) {
        if (object != null) {
            return;
        }
        isNotNull(object, "");//$NON-NLS-1$
    }
