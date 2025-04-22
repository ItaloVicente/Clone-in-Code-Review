    /**
     * Tests whether a part-specific context -- submitted via Java code -- is
     * matched properly. This is only using the part id. The test verifies that
     * it is active when the part is active, and not active when the part is not
     * active.
     *
     * @throws PartInitException
     *             If something goes wrong creating the part to which this
     *             handler is tied.
     *
     */
    public final void testPartIdSubmission() throws PartInitException {
        final String testContextId = "org.eclipse.ui.tests.contexts.Bug74990";
        final IWorkbenchContextSupport contextSupport = fWorkbench
                .getContextSupport();
        final IContext testContext = contextSupport.getContextManager()
                .getContext(testContextId);
