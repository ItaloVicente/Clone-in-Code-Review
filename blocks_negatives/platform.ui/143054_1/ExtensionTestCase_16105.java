    /**
     * Tests that the "scopes" element in the "org.eclipse.ui.commands"
     * extension point can be read in as a context by Eclipse. This element is
     * currently deprecated.
     *
     * @throws NotDefinedException
     *             This shouldn't really be possible, as the test should fail
     *             gracefully before this could happen.
     */
    public final void testCommandsScopes() throws NotDefinedException {
        final IWorkbenchContextSupport contextSupport = fWorkbench
                .getContextSupport();
        final IContextManager contextManager = contextSupport
                .getContextManager();
