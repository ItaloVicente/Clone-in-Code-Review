    /**
     * Assert that this parameter is null.
     * @param name of parameter
     * @param parameter itself
     */
    public static void checkNullMandatory(final String name,
                                          final Object parameter) {
        if (parameter != null) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be null!");
        }
    }
