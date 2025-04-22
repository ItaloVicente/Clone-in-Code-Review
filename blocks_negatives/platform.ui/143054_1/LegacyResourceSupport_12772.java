    /**
     * Adapts an object to a specified objectClass considering the Legacy resource
     * support. Non resource objectClasses are adapted using the <code>IAdapterManager</code>
     * and this may load the plug-in that contributes the adapter factory.
     * <p>
     * The returned selection will be of the same size as the original, and elements that could
     * not be adapted are added to the returned selection as is.
     * </p>
     * @param element the element to adapt
     * @param objectClass the class name to adapt the selection to
     * @return an adapted element or <code>null</code> if the
     * element could not be adapted.
     *
     * @since 3.1
     */
    public static Object getAdapter(Object element, String objectClass) {
