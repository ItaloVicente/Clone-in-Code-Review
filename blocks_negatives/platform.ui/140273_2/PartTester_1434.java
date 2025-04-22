    private PartTester() {
    }

    /**
     * Sanity-check the public interface of the editor. This is called on every editor after it
     * is fully initiallized, but before it is actually connected to the editor reference or the
     * layout. Calls as much of the editor's public interface as possible to test for exceptions,
     * and tests the return values for glaring faults. This does not need to be an exhaustive conformance
     * test, as it is called every time an editor is opened and it needs to be efficient.
     * The part should be unmodified when the method exits.
     *
     * @param part
     */
    public static void testEditor(IEditorPart part) throws Exception {
        testWorkbenchPart(part);

        Assert.isTrue(part.getEditorSite() == part.getSite(),
