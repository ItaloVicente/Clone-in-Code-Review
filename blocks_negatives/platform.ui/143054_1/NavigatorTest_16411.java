    public NavigatorTest(String testName) {
        super(testName);
    }

    /**
     * Tests that the Navigator is initially populated with
     * the correct elements from the workspace.
     */
    public void testInitialPopulation() throws CoreException, PartInitException {
        createTestFile();
        showNav();

        ISelectionProvider selProv = navigator.getSite().getSelectionProvider();
        StructuredSelection sel = new StructuredSelection(testFile);
        selProv.setSelection(sel);
        assertEquals(sel.size(), ((IStructuredSelection)selProv.getSelection()).size());
