    /**
     * <code>AssertionFailedException</code> is a runtime exception thrown
     * by some of the methods in <code>Assert</code>.
     * <p>
     * This class is not declared public to prevent some misuses; programs that catch
     * or otherwise depend on assertion failures are susceptible to unexpected
     * breakage when assertions in the code are added or removed.
     * </p>
     */
    private static class AssertionFailedException extends RuntimeException {
