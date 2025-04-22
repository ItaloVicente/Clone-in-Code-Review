    /**
     * Returns the last mock action delegate which was created.
     */
    protected MockActionDelegate getDelegate() throws Throwable {
        MockActionDelegate delegate = MockActionDelegate.lastDelegate;
        assertNotNull(delegate);
        return delegate;
    }
