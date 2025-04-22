    /**
     * Tests that the "org.eclipse.ui.acceleratorScopes" extension point can be
     * read in by Eclipse. This extension point is currently deprecated.
     *
     * @throws NotDefinedException
     *             This shouldn't really be possible, as the test should fail
     *             gracefully before this could happen.
     */
    public final void testAcceleratorScopes() throws NotDefinedException {
        final IWorkbenchContextSupport contextSupport = fWorkbench
                .getContextSupport();
        final IContextManager contextManager = contextSupport
                .getContextManager();
