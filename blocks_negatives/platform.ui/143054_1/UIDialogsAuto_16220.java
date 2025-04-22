        DialogCheck.assertDialogTexts(dialog);
    }

    public void testNewFileType() {
        Dialog dialog = new FileExtensionDialog(getShell());
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testProgressInformation() {
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
        dialog.setBlockOnOpen(true);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testSaveAs() {
        Dialog dialog = new SaveAsDialog(getShell());
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testSavePerspective() {
        PerspectiveRegistry reg = (PerspectiveRegistry) WorkbenchPlugin
                .getDefault().getPerspectiveRegistry();
        SavePerspectiveDialog dialog = new SavePerspectiveDialog(getShell(),
                reg);
        IPerspectiveDescriptor description = reg
                .findPerspectiveWithId(getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage()
                        .getPerspective().getId());
        dialog.setInitialSelection(description);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testSelectPerspective() {
        Dialog dialog = new SelectPerspectiveDialog(getShell(), PlatformUI
                .getWorkbench().getPerspectiveRegistry());
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testSelectTypes() {
        Dialog dialog = new TypeFilteringDialog(getShell(), null);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testShowView() {
    	IWorkbench workbench = getWorkbench();

    	Shell shell = workbench.getActiveWorkbenchWindow().getShell();
