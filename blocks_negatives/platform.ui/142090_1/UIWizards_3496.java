    public void testNewProject() {
        NewWizard wizard = new NewWizard();
        wizard.setProjectsOnly(true);
        ISelection selection = getWorkbench().getActiveWorkbenchWindow()
                .getSelectionService().getSelection();
        IStructuredSelection selectionToPass = null;
        if (selection instanceof IStructuredSelection) {
