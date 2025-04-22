    /**
     * Empty selections via different constructors.
     * Regression test for bug 40245.
     */
    public void testEquals7() {
        StructuredSelection empty1 = new StructuredSelection();
        StructuredSelection empty2 = new StructuredSelection(new Object[0]);
