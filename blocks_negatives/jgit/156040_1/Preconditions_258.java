    /**
     * Assert that this parameter is not empty. It will test for null and also the size of this array.
     * @param name of parameter
     * @param parameter itself
     */
    public static void checkNotEmpty(final String name,
                                     final Map<?, ?> parameter) {
        if (parameter == null || parameter.size() == 0) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
        }
    }
