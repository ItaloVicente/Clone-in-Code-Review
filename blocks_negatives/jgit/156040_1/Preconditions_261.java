    /**
     * Assert that this parameter is not null.
     * @param name of parameter
     * @param parameter itself
     */
    public static <T> T checkNotNull(final String name,
                                     final T parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
        }
        return parameter;
    }
