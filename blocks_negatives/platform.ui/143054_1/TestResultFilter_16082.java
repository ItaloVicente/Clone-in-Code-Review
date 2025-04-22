    /**
     * Creates a new filter that accepts the given test result
     *
     * @param toAccept
     */
    public TestResultFilter(TestResult toAccept) {
        if (toAccept.getException() != null) {
            result = null;
            expectedException = toAccept.getException().toString();
        } else {
            result = toAccept.getReturnValue();
            expectedException = null;
        }
    }
