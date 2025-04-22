    /**
     * Adds a method name to the call history.
     *
     * @param methodName the name of a method
     */
    public void add(String methodName) {
        testMethodName(methodName);
        methodList.add(methodName);
    }
