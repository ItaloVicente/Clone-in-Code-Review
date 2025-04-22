    /**
     * Tests that the currently preferred way of specifiying contexts can be
     * read in properly by Eclipse. This uses all of the non-deprecated
     * attributes.
     *
     * @throws NotDefinedException
     *             This shouldn't really be possible, as the test should fail
     *             gracefully before this could happen.
     */
    public final void testContexts() throws NotDefinedException {
        final IWorkbenchContextSupport contextSupport = fWorkbench
                .getContextSupport();
        final IContextManager contextManager = contextSupport
                .getContextManager();
