    /**
     * Assert that this parameter is not empty. It will test for null and also the size of this array.
     * @param <T> type of the array
     * @param name of parameter
     * @param parameter itself
     */
    public static <T> T[] checkNotEmpty(final String name,
                                        final T[] parameter) {
        if (parameter == null || parameter.length == 0) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
        }
        return parameter;
    }
