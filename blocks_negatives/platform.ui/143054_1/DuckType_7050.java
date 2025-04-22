    /**
     * Indicates if object is a (DuckType) instace of intrface.  That is,
     * is every method in intrface present on object.
     *
     * @param intrface The interface to implement
     * @param object The object to test
     * @return true if every method in intrface is present on object.  false otherwise
     */
    public static boolean instanceOf(Class intrface, Object object) {
        final Method[] methods = intrface.getMethods();
        Class candclass=object.getClass();
        for (int methodidx = 0; methodidx < methods.length; methodidx++) {
            Method method=methods[methodidx];
            try {
                candclass.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
        return true;
    }
