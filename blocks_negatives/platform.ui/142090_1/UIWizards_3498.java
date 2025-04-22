    public void testNewResource() {
        NewWizard wizard = new NewWizard();
        ISelection selection = getWorkbench().getActiveWorkbenchWindow()
                .getSelectionService().getSelection();
        IStructuredSelection selectionToPass = null;
        if (selection instanceof IStructuredSelection) {
