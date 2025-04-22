        ISelection selection = workbenchWindow.getSelectionService()
                .getSelection();
        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
        if (selection instanceof IStructuredSelection) {
            selectionToPass = (IStructuredSelection) selection;
        } else {
            Class resourceClass = LegacyResourceSupport.getResourceClass();
            if (resourceClass != null) {
                IWorkbenchPart part = workbenchWindow.getPartService()
                        .getActivePart();
                if (part instanceof IEditorPart) {
                    IEditorInput input = ((IEditorPart) part).getEditorInput();
