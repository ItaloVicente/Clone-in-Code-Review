        IWorkbench workbench = PlatformUI.getWorkbench();
        NewWizard wizard = new NewWizard();
        wizard.setProjectsOnly(true);
        ISelection selection = window.getSelectionService().getSelection();
        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
        if (selection instanceof IStructuredSelection) {
