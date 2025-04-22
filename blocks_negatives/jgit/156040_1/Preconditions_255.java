    /**
     * Assert that this parameter is marked as valid by the condition passed as parameter.
     * @param name of parameter
     * @param condition itself
     */
    public static void checkCondition(final String name,
                                      final boolean condition) {
        if (!condition) {
            throw new IllegalStateException("Condition '" + name + "' is invalid!");
        }
    }
