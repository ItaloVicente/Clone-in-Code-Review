    }

    /**
     * Returns <code>true</code> if the provided type name is an
     * <code>IResource</code>, and <code>false</code> otherwise.
     * @param objectClassName
     * @return <code>true</code> if the provided type name is an
     * <code>IResource</code>, and <code>false</code> otherwise.
     *
     * @since 3.1
     */
    public static boolean isResourceType(String objectClassName) {
        for (String resourceClassName : resourceClassNames) {
            if (resourceClassName.equals(objectClassName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns <code>true</code> if the provided type name is an
     * <code>"org.eclipse.core.resources.mapping.ResourceMapping"</code>, and <code>false</code> otherwise.
     * @param objectClassName
     * @return <code>true</code> if the provided type name is an
     * <code>"org.eclipse.core.resources.mapping.ResourceMapping"</code>, and <code>false</code> otherwise.
     *
     * @since 3.1
     */
    public static boolean isResourceMappingType(String objectClassName) {
    }

    /**
     * Returns the class search order starting with <code>extensibleClass</code>.
     * The search order is defined in this class' comment.
     *
     * @since 3.1
     */
    private static boolean isInstanceOf(Class clazz, String type) {
