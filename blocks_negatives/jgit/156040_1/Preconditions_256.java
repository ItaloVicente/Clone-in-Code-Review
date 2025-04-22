    /**
     * Assert that this parameter is not null, as also each item of the array is not null.
     * @param <T> parameter type
     * @param name of parameter
     * @param parameters itself
     */
    public static <T> void checkEachParameterNotNull(final String name,
                                                     final T... parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
        }
        for (final Object parameter : parameters) {
            if (parameter == null) {
                throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
            }
        }
    }
