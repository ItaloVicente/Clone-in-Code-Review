    /**
     * Assert that this parameter is not empty. It trims the parameter to see if have any valid data on that.
     * @param name of parameter
     * @param parameter itself
     */
    public static String checkNotEmpty(final String name,
                                       final String parameter) {
        if (parameter == null || parameter.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
        }
        return parameter;
    }
