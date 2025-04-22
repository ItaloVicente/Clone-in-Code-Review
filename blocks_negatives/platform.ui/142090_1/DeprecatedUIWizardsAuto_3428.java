    /**
     * Test for bug 30719 [Linked Resources] NullPointerException when setting filename for WizardNewFileCreationPage
     */
    public void testNewFile2() {
        BasicNewFileResourceWizard wizard = new BasicNewFileResourceWizard() {
            @Override
