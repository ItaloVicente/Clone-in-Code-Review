        DialogCheck.assertDialog(dialog);
    }

    public void testNewFileType() {
        Dialog dialog = new FileExtensionDialog(getShell());
        DialogCheck.assertDialog(dialog);
    }

    public void testProgressInformation() {
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
        dialog.setBlockOnOpen(true);
        DialogCheck.assertDialog(dialog);
    }

    public void testSaveAs() {
        Dialog dialog = new SaveAsDialog(getShell());
        DialogCheck.assertDialog(dialog);
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
        DialogCheck.assertDialog(dialog);
    }

