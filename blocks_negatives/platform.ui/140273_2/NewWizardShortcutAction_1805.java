
        INewWizard wizard;
        try {
            wizard = (INewWizard) wizardElement.createWizard();
        } catch (CoreException e) {
            ErrorDialog.openError(window.getShell(), WorkbenchMessages.NewWizardShortcutAction_errorTitle,
                    WorkbenchMessages.NewWizardShortcutAction_errorMessage,
                    e.getStatus());
            return;
        }

        ISelection selection = window.getSelectionService().getSelection();
        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
        if (selection instanceof IStructuredSelection) {
            selectionToPass = wizardElement
                    .adaptedSelection((IStructuredSelection) selection);
        } else {
            IWorkbenchPart part = window.getPartService().getActivePart();
            if (part instanceof IEditorPart) {
                IEditorInput input = ((IEditorPart) part).getEditorInput();
                Class fileClass = LegacyResourceSupport.getFileClass();
                if (input != null && fileClass != null) {
