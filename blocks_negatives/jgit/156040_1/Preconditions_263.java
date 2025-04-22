    public static <T> T checkInstanceOf(final String name,
                                        Object parameter,
                                        final Class<T> clazz) {
        checkNotNull(name,
                     parameter);
        checkNotNull("clazz",
                     clazz);
        if (!clazz.isInstance(parameter)) {
            throw new IllegalArgumentException("Parameter named '" + name + "' is not instance of clazz '" + clazz.getName() + "'!");
        }
