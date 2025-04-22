    /**
     * Returns whether a method has been called.
     *
     * @param methodName a method name
     * @return <code>true</code> if the method was called
     */
    public boolean contains(String methodName) {
        testMethodName(methodName);
        return methodList.contains(methodName);
    }
