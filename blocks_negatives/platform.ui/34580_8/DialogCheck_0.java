     * Asserts that a given dialog is not null and that it passes
     * certain visual tests.  These tests will be verified manually
     * by the tester using an input dialog.  Use this assert method
     * to verify a dialog's sizing, initial focus, or accessiblity.
     * To ensure that both the input dialog and the test dialog are
     * accessible by the tester, the getShell() method should be used
     * when creating the test dialog.
     *
     * Example usage:
     * <code>Dialog dialog = new AboutDialog( DialogCheck.getShell() );
     * DialogCheck.assertDialog(dialog, this);</code>
     *
     * @param dialog the test dialog to be verified.
     * @param assert this is the test case object, assertions will be
     * executed on this object.
     */
