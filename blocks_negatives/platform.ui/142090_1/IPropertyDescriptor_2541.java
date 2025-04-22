    /**
     * Returns the help context id for this property or
     * <code>null</code> if this property has no help context id.
     * <p>
     * NOTE: Help support system API's changed since 2.0 and arrays
     * of contexts are no longer supported.
     * </p>
     * <p>
     * Thus the only valid non-<code>null</code> return type for this method
     * is a <code>String</code> representing a context id. The previously
     * valid return types are deprecated. The plural name for this method
     * is unfortunate.
     * </p>
     *
     * @return the help context id for this entry
     */
    public Object getHelpContextIds();
