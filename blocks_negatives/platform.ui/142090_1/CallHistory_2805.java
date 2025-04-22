    /**
     * Returns whether a list of methods have been called in
     * order.
     *
     * @param testNames an array of the method names in the order they are expected
     * @return <code>true</code> if the methods were called in order
     */
    public boolean verifyOrder(String[] testNames)
            throws IllegalArgumentException {
        int testIndex = 0;
        int testLength = testNames.length;
        if (testLength == 0)
            return true;
